package com.RevHire.RevHire1.dao;

import java.util.List;

import com.RevHire.RevHire1.models.Skills;

public interface SkillsDAO {
    int getOrInsertSkill(String skillName);
    List<Skills> getAllSkills();
}