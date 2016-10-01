package io.github.evilofsoul.openwol.utils;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import io.github.evilofsoul.openwol.core.Machine;

/**
 * Created by Yevhenii on 01.10.2016.
 */

public class MachineNameComparator implements Comparator<Machine> {
    @Override
    public int compare(Machine machine, Machine machine2) {
        Collator collator = Collator.getInstance();
        return collator.compare(machine.getName(), machine2.getName());
    }
}
