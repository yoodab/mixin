package com.cos.mixin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.mixin.domain.school.SchoolRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SchoolService {

	private final SchoolRepository schoolRepository;
	
}
