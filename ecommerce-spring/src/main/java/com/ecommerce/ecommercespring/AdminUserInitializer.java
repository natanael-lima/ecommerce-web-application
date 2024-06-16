package com.ecommerce.ecommercespring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.service.UserService;

@Component
public class AdminUserInitializer implements ApplicationRunner {
	@Autowired 
    private UserService userService;
	
	@Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.lastname}")
    private String adminLastname;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		try {


            if (!userService.existsAdminRole() && !userService.existsUsername("prueba")) {
                User admin = new User();
                admin.setName(adminName);
                admin.setLastname(adminLastname);
                admin.setUsername(adminUsername);
                admin.setPassword(adminPassword);
                userService.saveUser(admin);
            }
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la inicialización
            e.printStackTrace();
        }
        
	}
}
