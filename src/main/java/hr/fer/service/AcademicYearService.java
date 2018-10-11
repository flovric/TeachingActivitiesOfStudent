package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.AcademicYear;
import hr.fer.repository.AcademicYearRepository;

@Service
public class AcademicYearService {
	
	@Autowired
	AcademicYearRepository academicYearRepository;
	
	public List<AcademicYear> findAll() {
		return (List<AcademicYear>) academicYearRepository.findAll();
	}

	public void deleteAcademicYear(long id) {
		academicYearRepository.delete(id);
		
	}

	public void saveAcademicYear(AcademicYear academicYear) {
		academicYearRepository.save(academicYear);
		
	}
	
	public AcademicYear findAcademicYearById(long id) {
		return academicYearRepository.findOne(id);
		
	}

}
