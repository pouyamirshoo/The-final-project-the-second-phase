package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Admin;
import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Request;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.entity.enums.ExpertCondition;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AdminService {

   private final AdminRepository adminRepository;
   private final ExpertService expertService;
   private final RequestService requestService;
   private final SubDutyService subDutyService;

   public void saveAdmin(Admin admin){
      adminRepository.save(admin);
   }

   public void adminSignIn(String username, String password) {
      adminRepository.findByUsernameAndPassword(username, password).orElseThrow(() ->
              new NotFoundException("wrong username or password"));
   }

   public void addExpertToSubDutyAuto(int id,Expert expert){
      expertService.updateExpertCondition(ExpertCondition.ACCEPTED,id);
      Request request = requestService.findByExpert(expert);
      List<SubDuty> requestSubDuties = request.getSubDuties();
      for (SubDuty subDuty : requestSubDuties){
         expert.getSubDuties().add(subDuty);
      }
      expertService.validate(expert);
   }

   public void addExpertToSubDutyManual(Expert expert,List<Integer> subDutyId){
      expertService.updateExpertCondition(ExpertCondition.ACCEPTED,expert.getId());
      List<SubDuty> createdSubDutyList = creatExpertSubDutyList(subDutyId);
      for (SubDuty subDuty : createdSubDutyList){
         expert.getSubDuties().add(subDuty);
      }
      expertService.validate(expert);
   }

   public List<SubDuty> creatExpertSubDutyList(List<Integer> subDutiesId){
      List<SubDuty> subDuties = new ArrayList<>();
      for (int id : subDutiesId) {
         SubDuty subDuty = subDutyService.findById(id);
         subDuties.add(subDuty);
      }
      return subDuties;
   }
}
