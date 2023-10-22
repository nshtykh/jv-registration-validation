package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid_login";
    private static final String VALID_LOGIN_EDGE_CASE = "login6";
    private static final String VALID_PASSWORD = "valid_password";
    private static final String VALID_PASSWORD_EDGE_CASE = "123456";
    private static final int VALID_AGE = 25;
    private static final int VALID_AGE_EDGE_CASE = 18;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_allValidData_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_allValidDataEdgeCase_Ok() {
        user.setLogin(VALID_LOGIN_EDGE_CASE);
        user.setPassword(VALID_PASSWORD_EDGE_CASE);
        user.setAge(VALID_AGE_EDGE_CASE);
        User expected = storageDao.add(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_sameLogin_notOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User user2 = new User();
        user2.setLogin(VALID_LOGIN);
        user2.setPassword(VALID_PASSWORD_EDGE_CASE);
        user2.setAge(VALID_AGE);
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_login3Length_notOk() {
        user.setLogin("abc");
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_login5Length_notOk() {
        user.setLogin("abc12");
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password3Length_notOk() {
        user.setPassword("123");
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password5Length_notOk() {
        user.setPassword("12345");
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIs7_notOk() {
        user.setAge(7);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIs12_notOk() {
        user.setAge(12);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIs17_notOk() {
        user.setAge(17);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNegativeNumber_notOk() {
        user.setAge(-8);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
