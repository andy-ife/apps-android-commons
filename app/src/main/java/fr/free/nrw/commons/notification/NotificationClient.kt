package fr.free.nrw.commons.notification

import fr.free.nrw.commons.auth.csrf.CsrfTokenClient
import fr.free.nrw.commons.auth.csrf.InvalidLoginTokenException
import fr.free.nrw.commons.di.NetworkingModule
import fr.free.nrw.commons.notification.models.Notification
import fr.free.nrw.commons.notification.models.NotificationType
import fr.free.nrw.commons.utils.DateUtil
import fr.free.nrw.commons.wikidata.mwapi.MwQueryResponse
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import fr.free.nrw.commons.wikidata.model.notifications.Notification as WikimediaNotification

@Singleton
class NotificationClient
    @Inject
    constructor(
        @param:Named(NetworkingModule.NAMED_COMMONS_CSRF) private val csrfTokenClient: CsrfTokenClient,
        private val service: NotificationInterface,
    ) {
        fun getNotifications(archived: Boolean): Single<List<Notification>> =
            service
                .getAllNotifications(
                    wikiList = "wikidatawiki|commonswiki|enwiki",
                    filter = if (archived) "read" else "!read",
                    continueStr = null,
                ).map {
                    it.query()?.notifications()?.list() ?: emptyList()
                }.flatMap {
                    Observable.fromIterable(it)
                }.map {
                    it.toCommonsNotification()
                }.toList()

        fun markNotificationAsRead(notificationId: String?): Observable<Boolean> =
            try {
                service
                    .markRead(
                        token = csrfTokenClient.getTokenBlocking(),
                        readList = notificationId,
                        unreadList = "",
                    ).map(MwQueryResponse::success)
            } catch (throwable: Throwable) {
                if (throwable is InvalidLoginTokenException) {
                    Observable.error(throwable)
                } else {
                    Observable.just(false)
                }
            }

        private fun WikimediaNotification.toCommonsNotification() :
            Notification {
            val notificationText = contents?.compactHeader ?: ""
            val notificationType =
                if (notificationText.contains("Sent you an email", ignoreCase = true)) {
                    NotificationType.EMAIL
                } else {
                    NotificationType.UNKNOWN
                }

                return Notification(
                    notificationType = notificationType,
                    notificationText = notificationText,
                    date = DateUtil.getMonthOnlyDateString(getTimestamp()),
                    link = contents?.links?.getPrimary()?.url ?: "",
                    iconUrl = "",
                    notificationId = id().toString(),
                )
        }
    }
