package edu.unq.springboot.IntegrationTest.services;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import edu.unq.springboot.models.Job;
import edu.unq.springboot.models.User;
import edu.unq.springboot.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    public void beforeEach() {
        User usuario = new User();
        usuario.setEmail("email");
        usuario.setFirstName("fname");
        usuario.setLastName("lname");
        usuario.setPassword("pass");
        usuario.setUsername("nick");
        usuario.setRecruiter(false);
        userService.create(usuario);
        User usuarioDos = new User("DosSantos", "pass", "fname", "lname", "correo", true);
        userService.create(usuarioDos);
    }

    @Test
    public void traigoUnUsuarioDesdeLaBasePorSuUsername() {
        User usuario = userService.findByUsername("nick");
        Assert.assertNotNull(usuario.getId());
        Assert.assertEquals("nick", usuario.getUsername());
        Assert.assertEquals("pass", usuario.getPassword());
        Assert.assertEquals("fname", usuario.getFirstName());
        Assert.assertEquals("lname", usuario.getLastName());
        Assert.assertEquals("email", usuario.getEmail());
        Assert.assertFalse(usuario.isRecruiter());
        Assert.assertEquals(0 , usuario.getJobs().size());
    }

    @Test
    public void validoUnLogInExitoso() {
        Assert.assertTrue(userService.validateUser("DosSantos", "pass"));
    }

    @Test
    public void validoVariosIntentosDeLogInFallidos() {
        Assert.assertNull(userService.validateUser("nick", ""));
        Assert.assertNull(userService.validateUser("", "pass"));
        Assert.assertNull(userService.validateUser("", ""));
        Assert.assertNull(userService.validateUser("nick", ""));
        Assert.assertNull(userService.validateUser("", "pass"));
    }

    @Test
    public void agregoUnTrabajoAUnUsuario() {
        User usuario = userService.findByUsername("nick");
        Job trabajo = new Job(usuario, "Titulo", "Descripcion", LocalDate.parse("2010-10-20"),
        		LocalDate.parse("2015-08-10"), "www.link.com", "http://img.us", 1);

        usuario = userService.addJob(trabajo, usuario);
        Assert.assertEquals(1, usuario.getJobs().size());
        trabajo = usuario.getJobs().get(0);
        Assert.assertNotNull(trabajo.getId());
        Assert.assertEquals(usuario, trabajo.getOwner());
        Assert.assertEquals("Titulo", trabajo.getTitulo());
        Assert.assertEquals("Descripcion", trabajo.getDescripcion());
        Assert.assertEquals("2010-10-20", trabajo.getFechaInicioTrabajo().toString());
        Assert.assertEquals("2015-08-10", trabajo.getFechaFinTrabajo().toString());
        Assert.assertEquals("www.link.com", trabajo.getEnlace());
    }
    
    @Test
    public void conUnUsuarioNoRecruiterYUnoRecruiterAlPedirLosNoRecruitersSoloSeRecibeElNoRecruiter() throws Exception {
    	List<User> noRecruiters = this.userService.getNonRecruiters();
    	
    	Assert.assertFalse(noRecruiters.get(0).isRecruiter());
    	Assert.assertEquals("nick", noRecruiters.get(0).getUsername());
    	Assert.assertEquals(1, noRecruiters.size());
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAll();
    }

}
