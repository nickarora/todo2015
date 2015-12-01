package nick.arora.todo2015.persistedDeviceId;

import android.content.SharedPreferences;

import com.google.common.truth.Expect;
import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nick.arora.todo2015.BuildConfig;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class PersistedDeviceIdTest {

    private static final String SHARED_PREFERENCES_DEVICE_ID = "device_uuid";

    @Mock
    SharedPreferences mockSharedPreferences;

    @Mock
    SharedPreferences.Editor mockEditor;

    private PersistedDeviceId persistedDeviceId;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        when(mockEditor.putString(any(String.class), any(String.class))).thenReturn(mockEditor);
    }

    @Test
    public void getString_returnsUUIDFromSharedPreferences() throws Exception {
        when(mockSharedPreferences.getString(BuildConfig.TODO_DEVICE_KEY, null)).thenReturn(SHARED_PREFERENCES_DEVICE_ID);
        persistedDeviceId = new PersistedDeviceId(mockSharedPreferences);

        String deviceId = persistedDeviceId.getString();
        assertThat(deviceId).isEqualTo(SHARED_PREFERENCES_DEVICE_ID);
    }

    @Test
    public void getString_generatesAndSavesNewUUIDIfSharedPreferencesIsEmpty() throws Exception {
        when(mockSharedPreferences.getString(BuildConfig.TODO_DEVICE_KEY, null)).thenReturn(null);
        persistedDeviceId = new PersistedDeviceId(mockSharedPreferences);

        String deviceId = persistedDeviceId.getString();
        assertThat(deviceId).hasLength(36);
        verify(mockSharedPreferences).edit();
        verify(mockEditor).apply();
    }

    @Test
    public void getString_cachesUUIDAfterFirstCallToSharedPreferences() throws Exception {
        when(mockSharedPreferences.getString(BuildConfig.TODO_DEVICE_KEY, null)).thenReturn(null);
        persistedDeviceId = new PersistedDeviceId(mockSharedPreferences);

        String deviceId1 = persistedDeviceId.getString();
        String deviceId2 = persistedDeviceId.getString();

        assertThat(deviceId1).isEqualTo(deviceId2);
        // two calls to getString results in one call to retrieve and one call to save
        verify(mockSharedPreferences).getString(BuildConfig.TODO_DEVICE_KEY, null);
        verify(mockEditor).apply();
    }


}