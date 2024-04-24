package io.platformengineer.rdicpfhighvolume.initialization;

import io.platformengineer.rdicpfhighvolume.address.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.platformengineer.rdicpfhighvolume.person.PersonRepository;
import io.platformengineer.rdicpfhighvolume.utils.DataGenerationService;
import io.platformengineer.rdicpfhighvolume.person.Person;

@Service
public class ControlledDataInitializationService {


    @Autowired
    private DataGenerationService dataGenerationService;


    public void insertControlledData() {
        dataGenerationService.insertControlledData();
    }
}
