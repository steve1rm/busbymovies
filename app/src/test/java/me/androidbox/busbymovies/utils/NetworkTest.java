package me.androidbox.busbymovies.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 12/16/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkTest {
    @Mock
    private ConnectivityManager connectivityManager;

    private IConnectivityProvider connectivityProvider;

    @Before
    public void setup() {
        connectivityProvider = new Network(connectivityManager);
    }

    @Test(expected = NullPointerException.class)
    public void testNetwork_ThrowNullException_whenNull() {
        connectivityProvider = new Network(null);
    }

    @Test
    public void testIsConnected_returnFalse_whenNetworkInfo_equalNull() {
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(null);

        assertThat(connectivityProvider.isConnected(), is(false));
    }

    @Test
    public void testIsConnected_returnFalse_whenNetworkInfo_isNotConnected() {
        final NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(false);

        assertThat(connectivityProvider.isConnected(), is(false));
    }


    @Test
    public void testIsConnected_returnTrue_whenNetworkInfo_isConnected() {
        final NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(true);

        assertThat(connectivityProvider.isConnected(), is(true));
    }

    @Test
    public void testGetType_returnNull_whenNetworkInfo_isNull() {
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(null);

        assertThat(connectivityProvider.getType(), is(nullValue()));
    }

    @Test
    public void testGetType_returnMOBILE_whenNetworkIsTYPE_MOBILE() {
        final NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);

        assertThat(connectivityProvider.getType(), is(IConnectivityProvider.TYPE.MOBILE));
    }

    @Test
    public void testGetType_returnWIFI_whenNetworkIsTYPE_WIFI() {
        final NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);

        assertThat(connectivityProvider.getType(), is(IConnectivityProvider.TYPE.WIFI));
    }

    @Test
    public void testGetType_returnOTHER_whenNetworkIsTYPE_OTHER() {
        final NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.getType()).thenReturn(-1);

        assertThat(connectivityProvider.getType(), is(IConnectivityProvider.TYPE.OTHER));
    }

    @Test
    public void testIsOnline_returnTrue_ifExitValueIsZero() throws InterruptedException {
        final Process process = mock(Process.class);

        when(process.waitFor()).thenReturn(0);

        assertThat(connectivityProvider.isOnline(process), is(true));
    }

    @Test
    public void testIsOnline_returnTrue_ifExitValueIsTwo() throws InterruptedException {
        final Process process = mock(Process.class);

        when(process.waitFor()).thenReturn(2);

        assertThat(connectivityProvider.isOnline(process), is(true));
    }

    @Test
    public void testIsOnline_returnFalse_ifExitValueIsNotZeroOrTwo() throws InterruptedException {
        final Process process = mock(Process.class);

        when(process.waitFor()).thenReturn(1);

        assertThat(connectivityProvider.isOnline(process), is(false));
    }

    @Test
    public void testIsOnline_returnFalse_ifInterruptedException_isThrown() throws InterruptedException {
        final Process process = mock(Process.class);

        when(process.waitFor()).thenThrow(new InterruptedException("Failed to get exitValue"));

        assertThat(connectivityProvider.isOnline(process), is(false));
    }
}