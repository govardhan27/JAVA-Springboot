package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.model.Person;

import org.springframework.stereotype.Repository;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {
    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllpeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> foundPerson = selectPersonById(id);
        if (foundPerson.isEmpty()) {
            return 0;
        }
        DB.remove(foundPerson.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person updatePerson) {
        return selectPersonById(id).map(person -> {
            int indexOfpersonToUpdate = DB.indexOf(person);
            if (indexOfpersonToUpdate >= 0) {
                DB.set(indexOfpersonToUpdate, new Person(id, updatePerson.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }
}