package com.example.examinbackend;

import com.example.examinbackend.model.Machine;
import com.example.examinbackend.model.Part;
import com.example.examinbackend.model.Subassembly;
import com.example.examinbackend.service.MachineService;
import com.example.examinbackend.service.PartService;
import com.example.examinbackend.service.SubassemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test")
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
        partService.createPart(new Part("CPU", 1000L));
        partService.createPart(new Part("Laptop CPU", 800L));
        partService.createPart(new Part("Mac CPU", 1400L));
        partService.createPart(new Part("MacBook CPU", 1350L));
        partService.createPart(new Part("Gaming CPU", 2000L));
        partService.createPart(new Part("CPU Cooler", 600L));
        partService.createPart(new Part("Thermal Paste", 50L));

        //Motherboard
        partService.createPart(new Part("Motherboard", 2000L));
        partService.createPart(new Part("Laptop Motherboard", 1500L));
        partService.createPart(new Part("Mac Motherboard", 2200L));
        partService.createPart(new Part("Macbook Motherboard", 2400L));
        partService.createPart(new Part("Gaming Motherboard", 2800L));
        partService.createPart(new Part("BIOS FIRMWARE", 400L));
        partService.createPart(new Part("On-Board Audio and Network Card", 600L));

        //RAM
        partService.createPart(new Part("8gb RAM", 500L));
        partService.createPart(new Part("8gb*2 RAM", 1300L));
        partService.createPart(new Part("16gb RAM", 1200L));
        partService.createPart(new Part("32gb RAM", 2100L));
        partService.createPart(new Part("64gb RAM", 3000L));
        partService.createPart(new Part("Memory Slots", 300L));

        //GPU
        partService.createPart(new Part("GPU", 6300L));
        partService.createPart(new Part("Laptop GPU", 4500L));
        partService.createPart(new Part("Mac GPU", 7300L));
        partService.createPart(new Part("Macbook GPU", 7000L));
        partService.createPart(new Part("Gaming GPU", 10000L));
        partService.createPart(new Part("GPU Cooling System", 1200L));

        //endregion

        //region Subassemblies

        List<Part> CPU = new ArrayList<>(Arrays.asList(
                partService.getPartByName("CPU"),
                partService.getPartByName("CPU Cooler"),
                partService.getPartByName("Thermal Paste")
        ));

        subassemblyService.createSubassembly(new Subassembly("CPU", CPU));

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

        subassemblyService.createSubassembly(new Subassembly("Macbook CPU", macbookCPU));

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

        List<Part> laptopMotherboard = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Laptop Motherboard"),
                partService.getPartByName("BIOS FIRMWARE"),
                partService.getPartByName("On-Board Audio and Network Card")
        ));

        subassemblyService.createSubassembly(new Subassembly("Laptop Motherboard", laptopMotherboard));

        List<Part> macMotherboard = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Mac Motherboard"),
                partService.getPartByName("BIOS FIRMWARE"),
                partService.getPartByName("On-Board Audio and Network Card")
        ));

        subassemblyService.createSubassembly(new Subassembly("Mac Motherboard", macMotherboard));

        List<Part> macbookMotherboard = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Macbook Motherboard"),
                partService.getPartByName("BIOS FIRMWARE"),
                partService.getPartByName("On-Board Audio and Network Card")
        ));

        subassemblyService.createSubassembly(new Subassembly("Macbook Motherboard", macbookMotherboard));

        List<Part> gamingMotherboard = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Gaming Motherboard"),
                partService.getPartByName("BIOS FIRMWARE"),
                partService.getPartByName("On-Board Audio and Network Card")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gaming Motherboard", gamingMotherboard));

        List<Part> Ram = new ArrayList<>(Arrays.asList(
                partService.getPartByName("32gb RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Ram", Ram));

        List<Part> laptopRam = new ArrayList<>(Arrays.asList(
                partService.getPartByName("16gb RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Laptop Ram", laptopRam));

        List<Part> macRam = new ArrayList<>(Arrays.asList(
                partService.getPartByName("8gb*2 RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Mac Ram", macRam));

        List<Part> macBookRam = new ArrayList<>(Arrays.asList(
                partService.getPartByName("8gb RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Macbook Ram", macBookRam));


        List<Part> gamingRam = new ArrayList<>(Arrays.asList(
                partService.getPartByName("64gb RAM"),
                partService.getPartByName("Memory Slots")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gaming Ram", gamingRam));

        List<Part> gpu = new ArrayList<>(Arrays.asList(
                partService.getPartByName("GPU"),
                partService.getPartByName("GPU Cooling System")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gpu", gpu));

        List<Part> laptopGpu = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Laptop GPU"),
                partService.getPartByName("GPU Cooling System")
        ));

        subassemblyService.createSubassembly(new Subassembly("Laptop Gpu", laptopGpu));

        List<Part> macGpu = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Mac GPU"),
                partService.getPartByName("GPU Cooling System")
        ));

        subassemblyService.createSubassembly(new Subassembly("Mac Gpu", macGpu));

        List<Part> macbookGpu = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Macbook GPU"),
                partService.getPartByName("GPU Cooling System")
        ));

        subassemblyService.createSubassembly(new Subassembly("Macbook Gpu", macbookGpu));

        List<Part> gamingGpu = new ArrayList<>(Arrays.asList(
                partService.getPartByName("Gaming GPU"),
                partService.getPartByName("GPU Cooling System")
        ));

        subassemblyService.createSubassembly(new Subassembly("Gaming Gpu", gamingGpu));

        //endregion

        //region Machines

        List<Subassembly> windowsDesktop = new ArrayList<>();
        Arrays.asList("CPU", "Motherboard", "Ram", "Gpu").forEach(name -> {
            subassemblyService.getSubassemblyByName(name).ifPresent(windowsDesktop::add);
        });

        machineService.createMachine(new Machine("Windows Desktop", windowsDesktop));

        List<Subassembly> windowsLaptop = new ArrayList<>();
        Arrays.asList("Laptop CPU", "Laptop Motherboard", "Laptop Ram", "Laptop Gpu").forEach(name -> {
            subassemblyService.getSubassemblyByName(name).ifPresent(windowsLaptop::add);
        });

        machineService.createMachine(new Machine("Windows Laptop", windowsLaptop));

        List<Subassembly> macbook = new ArrayList<>();
        Arrays.asList("Macbook CPU", "Macbook Motherboard", "Macbook Ram", "Macbook Gpu").forEach(name -> {
            subassemblyService.getSubassemblyByName(name).ifPresent(macbook::add);
        });

        machineService.createMachine(new Machine("MacBook", macbook));

        List<Subassembly> Imac = new ArrayList<>();
        Arrays.asList("Mac CPU", "Mac Motherboard", "Mac Ram", "Mac Gpu").forEach(name -> {
            subassemblyService.getSubassemblyByName(name).ifPresent(Imac::add);
        });

        machineService.createMachine(new Machine("iMac", Imac));

        List<Subassembly> gamingDesktop = new ArrayList<>();
        Arrays.asList("Gaming CPU", "Gaming Motherboard", "Gaming Ram", "Gaming Gpu").forEach(name -> {
            subassemblyService.getSubassemblyByName(name).ifPresent(gamingDesktop::add);
        });

        machineService.createMachine(new Machine("Gaming Desktop", gamingDesktop));

        //endregion

        System.out.println("Initialized database");

    }
}