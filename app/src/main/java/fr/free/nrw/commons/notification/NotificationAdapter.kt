package fr.free.nrw.commons.notification

import fr.free.nrw.commons.notification.models.Notification
import fr.free.nrw.commons.upload.categories.BaseDelegateAdapter

internal class NotificationAdapter(
    onNotificationClicked: (Notification) -> Unit,
) : BaseDelegateAdapter<Notification>(
        notificationDelegate(onNotificationClicked),
        areItemsTheSame = { oldItem, newItem -> oldItem.notificationId == newItem.notificationId },
    )
