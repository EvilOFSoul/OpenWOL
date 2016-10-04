/*
 *     Copyright 2016 Yevhenii Bohdanets
 *
 *     This file is part of OpenWOL.
 *
 *     OpenWOL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     OpenWOL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.evilofsoul.openwol.core;

import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
/**
 * Created by Yevhenii on 03.10.2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MachineTest {
    Machine machine;

    @Before
    public void setUp(){
        machine = new Machine(
                1,
                "test 1",
                MacAddress.create("00-08-74-4C-7F-1D"),
                "192.168.0.1",
                7
        );
    }

    @Test
    public void writeToParcel(){
        Parcel parcel = Parcel.obtain();
        machine.writeToParcel(parcel,0);
        parcel.setDataPosition(0);
        Machine machineFromParcel = Machine.CREATOR.createFromParcel(parcel);
        assertThat(machine, samePropertyValuesAs(machineFromParcel));
    }

}
