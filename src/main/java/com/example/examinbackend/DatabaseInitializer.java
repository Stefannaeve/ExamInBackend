package com.example.examinbackend;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.service.MachineService;
import com.example.examinbackend.service.PartService;
import com.example.examinbackend.service.SubassemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final SubassemblyService subassemblyService;

    private final MachineService machineService;
    private final PartService partService;

    @Autowired
    public DatabaseInitializer(SubassemblyService subassemblyService, MachineService machineService, PartService partService) {
        this.subassemblyService = subassemblyService;
        this.machineService = machineService;
        this.partService = partService;
    }

    public void run(String... args) {

        //region Parts

        //CPU
        partService.createPart(new Part("CPU"));
        partService.createPart(new Part("Laptop CPU"));
        partService.createPart(new Part("Mac CPU"));
        partService.createPart(new Part("MacBook CPU"));
        partService.createPart(new Part("Gaming CPU"));
        partService.createPart(new Part("CPU Cooler"));
        partService.createPart(new Part("Thermal Paste"));

        //Motherboard
        partService.createPart(new Part("Motherboard"));
        partService.createPart(new Part("BIOS FIRMWARE"));
        partService.createPart(new Part("On-Board Audio and Network Card"));

        //RAM
        partService.createPart(new Part("8gb RAM"));
        partService.createPart(new Part("16gb RAM"));
        partService.createPart(new Part("32gb RAM"));
        partService.createPart(new Part("Memory Slots"));

        //GPU
        partService.createPart(new Part("GPU"));
        partService.createPart(new Part("Gaming GPU"));
        partService.createPart(new Part("GPU Cooling System"));

        //endregion

        //region Subassemblies

        List<Part> windowsCPU = new ArrayList<>(Arrays.asList(
                partService.getPartByName("CPU"),
                partService.getPartByName("CPU Cooler"),
                partService.getPartByName("Thermal Paste")
        ));

        subassemblyService.createSubassembly(new Subassembly("Windows CPU", windowsCPU));

        List<Part> laptopCPU = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Laptop CPU"),
                partService.getPartByName("CPU Cooler"),
                partService.getPartByName("Thermal Paste")
        ));

        subassemblyService.createSubassembly(new Subassembly("Laptop CPU", laptopCPU));

        List<Part> macCPU = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Mac CPU"),
                partService.getPartByName("CPU Cooler"),
                partService.getPartByName("Thermal Paste")
        ));

        subassemblyService.createSubassembly(new Subassembly("Mac CPU", macCPU));

        List<Part> macbookCPU = new ArrayList<>(Arrays.asList(
                partService.getPartByName("MacBook CPU"),
                partService.getPartByName("CPU Cooler"),
                partService.getPartByName("Thermal Paste")
        ));

        subassemblyService.createSubassembly(new Subassembly("MacBook CPU", macbookCPU));

        List<Part> gamingCPU = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Gaming CPU"),
                partService.getPartByName("CPU Cooler"),
                partService.getPartByName("Thermal Paste")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gaming CPU", gamingCPU));

        List<Part> motherboard = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Motherboard"),
                partService.getPartByName("BIOS FIRMWARE"),
                partService.getPartByName("On-Board Audio and Network Card")
        ));

        subassemblyService.createSubassembly(new Subassembly("Motherboard", motherboard));

        List<Part> windowsRam = new ArrayList<>(Arrays.asList(
                partService.getPartByName("16gb RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Windows Ram", windowsRam));

        List<Part> macRam = new ArrayList<>(Arrays.asList(
                partService.getPartByName("8gb RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Mac Ram", macRam));

        List<Part> gamingRam = new ArrayList<>(Arrays.asList(
                partService.getPartByName("32gb RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gaming Ram", gamingRam));

        List<Part> gpu = new ArrayList<>(Arrays.asList(
                partService.getPartByName("GPU"),
                partService.getPartByName("GPU Cooling System")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gpu", gpu));

        List<Part> gamingGpu = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Gaming GPU"),
                partService.getPartByName("GPU Cooling System")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gaming Gpu", gamingGpu));

        //endregion

        //region Machines

        List<Subassembly> windowsDesktop = new ArrayList<>(Arrays.asList(
                subassemblyService.getSubassemblyByName("Windows CPU"),
                subassemblyService.getSubassemblyByName("Motherboard"),
                subassemblyService.getSubassemblyByName("Windows Ram"),
                subassemblyService.getSubassemblyByName("Gpu")
        ));

        machineService.createMachine(new Machine("Windows Desktop", windowsDesktop));

        List<Subassembly> windowsLaptop = new ArrayList<>(Arrays.asList(
                subassemblyService.getSubassemblyByName("Laptop CPU"),
                subassemblyService.getSubassemblyByName("Motherboard"),
                subassemblyService.getSubassemblyByName("Windows Ram"),
                subassemblyService.getSubassemblyByName("Gpu")
        ));

        machineService.createMachine(new Machine("Windows Laptop", windowsLaptop));

        List<Subassembly> macbook = new ArrayList<>(Arrays.asList(
                subassemblyService.getSubassemblyByName("MacBook CPU"),
                subassemblyService.getSubassemblyByName("Motherboard"),
                subassemblyService.getSubassemblyByName("Mac Ram"),
                subassemblyService.getSubassemblyByName("Gpu")
        ));

        machineService.createMachine(new Machine("MacBook", macbook));

        List<Subassembly> Imac = new ArrayList<>(Arrays.asList(
                subassemblyService.getSubassemblyByName("Mac CPU"),
                subassemblyService.getSubassemblyByName("Motherboard"),
                subassemblyService.getSubassemblyByName("Mac Ram"),
                subassemblyService.getSubassemblyByName("Gpu")
        ));

        machineService.createMachine(new Machine("iMac", Imac));

        List<Subassembly> gamingDesktop = new ArrayList<>(Arrays.asList(
                subassemblyService.getSubassemblyByName("Gaming CPU"),
                subassemblyService.getSubassemblyByName("Motherboard"),
                subassemblyService.getSubassemblyByName("Gaming Ram"),
                subassemblyService.getSubassemblyByName("Gaming Gpu")
        ));

        machineService.createMachine(new Machine("Gaming Desktop", gamingDesktop));

        //endregion

    }
}
