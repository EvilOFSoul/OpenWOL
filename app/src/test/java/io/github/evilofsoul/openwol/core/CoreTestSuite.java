package io.github.evilofsoul.openwol.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import io.github.evilofsoul.openwol.core.MacAddressTest;
import io.github.evilofsoul.openwol.core.MagicPacketTest;


/**
 * Created by Yevhenii on 14.09.2016.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
        MacAddressTest.class,
        MagicPacketTest.class
})
public class CoreTestSuite{
}
