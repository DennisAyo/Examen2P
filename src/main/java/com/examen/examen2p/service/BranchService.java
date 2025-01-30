package com.examen.examen2p.service;

import com.examen.examen2p.model.Branch;
import com.examen.examen2p.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService implements Serializable {
    
    private static final Logger log = LoggerFactory.getLogger(BranchService.class);
    
    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        log.info("Obteniendo todas las sucursales");
        List<Branch> branches = branchRepository.findAll();
        log.info("Se encontraron {} sucursales", branches.size());
        return branches;
    }

    public Branch createBranch(Branch branch) {
        log.info("Creando nueva sucursal con nombre: {}", branch.getName());
        branch.setCreationDate(LocalDateTime.now());
        branch.setLastModifiedDate(LocalDateTime.now());
        Branch savedBranch = branchRepository.save(branch);
        log.info("Sucursal creada con ID: {}", savedBranch.getId());
        return savedBranch;
    }

    public Optional<Branch> getBranchById(String id) {
        log.info("Buscando sucursal con ID: {}", id);
        Optional<Branch> branch = branchRepository.findById(id);
        if (branch.isPresent()) {
            log.info("Sucursal encontrada");
        } else {
            log.error("No se encontró la sucursal con ID: {}", id);
        }
        return branch;
    }

    public Branch updateBranchPhone(String id, String newPhoneNumber) {
        Optional<Branch> branchOpt = branchRepository.findById(id);
        if (branchOpt.isPresent()) {
            Branch branch = branchOpt.get();
            branch.setPhoneNumber(newPhoneNumber);
            branch.setLastModifiedDate(LocalDateTime.now());
            return branchRepository.save(branch);
        }
        return null;
    }

    public Branch addHoliday(String branchId, Branch.BranchHoliday holiday) {
        Optional<Branch> branchOpt = branchRepository.findById(branchId);
        if (branchOpt.isPresent()) {
            Branch branch = branchOpt.get();
            branch.getBranchHolidays().add(holiday);
            return branchRepository.save(branch);
        }
        return null;
    }

    public Branch removeHolidays(String branchId) {
        Optional<Branch> branchOpt = branchRepository.findById(branchId);
        if (branchOpt.isPresent()) {
            Branch branch = branchOpt.get();
            branch.getBranchHolidays().clear();
            return branchRepository.save(branch);
        }
        return null;
    }

    public List<Branch.BranchHoliday> getBranchHolidays(String branchId) {
        Optional<Branch> branchOpt = branchRepository.findById(branchId);
        return branchOpt.map(Branch::getBranchHolidays).orElse(null);
    }

    public boolean isHoliday(String branchId, LocalDate date) {
        Optional<Branch> branchOpt = branchRepository.findById(branchId);
        if (branchOpt.isPresent()) {
            Branch branch = branchOpt.get();
            return branch.getBranchHolidays().stream()
                    .anyMatch(holiday -> holiday.getDate().equals(date));
        }
        return false;
    }
} 