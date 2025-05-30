package fr.free.nrw.commons.upload.categories

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.test.core.app.ApplicationProvider
import fr.free.nrw.commons.Media
import fr.free.nrw.commons.OkHttpConnectionFactory
import fr.free.nrw.commons.R
import fr.free.nrw.commons.TestCommonsApplication
import fr.free.nrw.commons.createTestClient
import fr.free.nrw.commons.databinding.UploadCategoriesFragmentBinding
import fr.free.nrw.commons.upload.UploadActivity
import fr.free.nrw.commons.upload.UploadBaseFragment
import io.reactivex.disposables.Disposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.powermock.reflect.Whitebox
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Method

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21], application = TestCommonsApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class UploadCategoriesFragmentUnitTests {
    private lateinit var fragment: UploadCategoriesFragment
    private lateinit var context: Context
    private lateinit var fragmentManager: FragmentManager
    private lateinit var layoutInflater: LayoutInflater

    @Mock
    private lateinit var subscribe: Disposable

    @Mock
    private lateinit var adapter: UploadCategoryAdapter

    @Mock
    private lateinit var callback: UploadBaseFragment.Callback

    @Mock
    private lateinit var presenter: CategoriesContract.UserActionListener

    @Mock
    private lateinit var media: Media

    private lateinit var binding: UploadCategoriesFragmentBinding

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext()
        OkHttpConnectionFactory.CLIENT = createTestClient()
        val activity = Robolectric.buildActivity(UploadActivity::class.java).create().get()
        fragment = UploadCategoriesFragment()
        fragment.callback = callback
        fragmentManager = activity.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(fragment, null)
        fragmentTransaction.commit()

        layoutInflater = LayoutInflater.from(activity)
        binding = UploadCategoriesFragmentBinding.inflate(layoutInflater)

        Whitebox.setInternalState(fragment, "subscribe", subscribe)
        Whitebox.setInternalState(fragment, "adapter", adapter)
        Whitebox.setInternalState(fragment, "presenter", presenter)
        Whitebox.setInternalState(fragment, "wikiText", "[[Category:Test]]")
    }

    @Test
    @Throws(Exception::class)
    fun checkFragmentNotNull() {
        Assert.assertNotNull(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun testOnCreateView() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.onCreateView(layoutInflater, null, null)
    }

    @Test
    @Throws(Exception::class)
    fun testInitMethod() {
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "init",
            )
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun `Test init when media is non null`() {
        Whitebox.setInternalState(fragment, "media", media)
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "init",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun testFragmentOnBecameVisible() {
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "onBecameVisible",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun testOnDestroyView() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.onDestroyView()
    }

    @Test
    @Throws(Exception::class)
    fun testShowProgress() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.showProgress(true)
    }

    @Test
    @Throws(Exception::class)
    fun testShowErrorString() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.showError("")
    }

    @Test
    @Throws(Exception::class)
    fun testShowErrorInt() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.showError(R.string.no_categories_found)
    }

    @Test
    @Throws(Exception::class)
    fun testSetCategoriesCaseNull() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.setCategories(null)
    }

    @Test
    @Throws(Exception::class)
    fun testSetCategoriesCaseNonNull() {
        fragment.setCategories(listOf())
    }

    @Test
    @Throws(Exception::class)
    fun testGoToNextScreen() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.goToNextScreen()
    }

    @Test
    @Throws(Exception::class)
    fun testShowNoCategorySelected() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.showNoCategorySelected()
    }

    @Test
    @Throws(Exception::class)
    fun testGetExistingCategories() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.getExistingCategories()
    }

    @Test
    @Throws(Exception::class)
    fun testGetFragmentContext() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.getFragmentContext()
    }

    @Test
    @Throws(Exception::class)
    fun testGoBackToPreviousScreen() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.goBackToPreviousScreen()
    }

    @Test
    @Throws(Exception::class)
    fun testShowProgressDialog() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.showProgressDialog()
    }

    @Test
    @Throws(Exception::class)
    fun testDismissProgressDialog() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.dismissProgressDialog()
    }

    @Test
    @Throws(Exception::class)
    fun `Test showNoCategorySelected when media is not null`() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        Whitebox.setInternalState(fragment, "media", media)
        fragment.showNoCategorySelected()
    }

    @Test
    @Throws(Exception::class)
    fun testOnNextButtonClicked() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.onNextButtonClicked()
    }

    @Test
    @Throws(Exception::class)
    fun `Test onNextButtonClicked when media is not null`() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        Whitebox.setInternalState(fragment, "media", media)
        fragment.onNextButtonClicked()
    }

    @Test
    @Throws(Exception::class)
    fun testOnPreviousButtonClicked() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        fragment.onPreviousButtonClicked()
    }

    @Test
    @Throws(Exception::class)
    fun testOnBecameVisible() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "onBecameVisible",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun testAddTextChangeListenerToEtSearch() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "addTextChangeListenerToEtSearch",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun testSearchForCategory() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "searchForCategory",
                String::class.java,
            )
        method.isAccessible = true
        method.invoke(fragment, "")
    }

    @Test
    @Throws(Exception::class)
    fun testInitRecyclerView() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "initRecyclerView",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun testInit() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "init",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun `Test init when media is not null`() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        Whitebox.setInternalState(fragment, "media", media)
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "init",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }

    @Test
    @Throws(Exception::class)
    fun `Test init when callback is null`() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val method: Method =
            UploadCategoriesFragment::class.java.getDeclaredMethod(
                "init",
            )
        method.isAccessible = true
        method.invoke(fragment)
    }
}
