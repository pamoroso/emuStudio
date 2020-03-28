package net.emustudio.plugins.device.mits88disk.cpmfs.commands;

import net.emustudio.plugins.device.mits88disk.cpmfs.CpmFile;
import net.emustudio.plugins.device.mits88disk.cpmfs.CpmFileSystem;

import java.io.IOException;
import java.util.Collection;

public class ListSubCommand implements CpmfsCommand.CpmfsSubCommand {

    @Override
    public void execute(CpmFileSystem fileSystem) throws IOException {
        printFiles(fileSystem.listValidFiles());
    }

    private static void printFiles(Collection<CpmFile> files) {
        for (CpmFile file : files) {
            System.out.println(file.toString());
        }
    }
}
