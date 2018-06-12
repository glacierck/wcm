package com.kongrongqi.shopmall;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kongrongqi.shopmall.shop.ManagerService;
import com.kongrongqi.shopmall.shop.ServiceA;
import com.kongrongqi.shopmall.shop.ServiceB;
import com.kongrongqi.shopmall.shop.ServiceTrusteeship;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {

        ManagerService lManager = new ManagerService(new ServiceTrusteeship());
        lManager.buyService();

    }
}
