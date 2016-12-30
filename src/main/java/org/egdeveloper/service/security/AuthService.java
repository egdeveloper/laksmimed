package org.egdeveloper.service.security;

import org.egdeveloper.data.model.Doctor;
import org.egdeveloper.service.data.IDoctorService;
import org.egdeveloper.web.form.Login;
import org.egdeveloper.web.form.Signup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service("userAuthService")
public class AuthService implements IAuthService {

    @Autowired
    @Qualifier("doctorService")
    private IDoctorService doctorService;

    @Override
    public void register(Signup newUserAccount){
        newUserAccount.setPassword(BCrypt.hashpw(newUserAccount.getPassword(), BCrypt.gensalt()));
        doctorService.addDoctor(newUserAccount.getDoctorAccount());
    }

    @Override
    public Doctor auth(Login login){
        Doctor doctor = doctorService.getDoctorByLogin(login.getLogin());
        if(doctor != null && BCrypt.checkpw(login.getPassword(), doctor.getPassword()))
            return doctor;
        return null;
    }
}