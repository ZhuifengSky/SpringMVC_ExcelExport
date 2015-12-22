package com.main.user.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.main.common.bean.Page;
import com.main.common.bean.PageBean;
import com.main.user.bean.StudentBean;
import com.main.user.dao.IStudentDao;
import com.main.user.model.Student;
import com.main.user.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService{

	@Resource
	private  IStudentDao studentDao; 
	@Override
	@Transactional
	public Student saveStudent(Student student) {
		return studentDao.addStudent(student);
	}
	@Override
	public Page<Student> findPageStudents(StudentBean queryBean,PageBean pageBean) {
		return studentDao.findPageStudents(queryBean,pageBean);
	}
	@Override
	public Student queryStident(String sid) {
		return studentDao.queryStident(sid);
	}
	@Override
	@Transactional
	public void deleteStudent(String sid) {
		studentDao.deleteStudent(sid);
		
	}
	@Override
	@Transactional
	public void updateStudent(Student student) {
		studentDao.updateStudent(student);
		
	}
	@Override
	public List<Student> findStudents(StudentBean queryBean) {
		return studentDao.findStudents(queryBean);
	}

}
