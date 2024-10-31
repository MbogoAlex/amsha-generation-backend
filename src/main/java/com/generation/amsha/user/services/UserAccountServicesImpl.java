package com.generation.amsha.user.services;

import com.generation.amsha.aws.service.AwsService;
import com.generation.amsha.savingsPlan.dao.SavingsPlanDao;
import com.generation.amsha.savingsPlan.model.SavingsPlan;
import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.dto.*;
import com.generation.amsha.user.mapper.UserAccountMapper;
import com.generation.amsha.user.model.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserAccountServicesImpl implements UserAccountServices{
    UserAccountMapper userAccountMapper = new UserAccountMapper();
    private final UserAccountDao userAccountDao;
    private final SavingsPlanDao savingsPlanDao;
    private final PasswordEncoder passwordEncoder;

    private final AwsService awsService;
    @Autowired
    public UserAccountServicesImpl(
            UserAccountDao userAccountDao,
            SavingsPlanDao savingsPlanDao,
            PasswordEncoder passwordEncoder,
            AwsService awsService
    ) {
        this.userAccountDao = userAccountDao;
        this.savingsPlanDao = savingsPlanDao;
        this.passwordEncoder = passwordEncoder;
        this.awsService = awsService;
    }
    @Transactional
    @Override
    public UserDto register(UserRegistrationDto userDto) {
        UserAccount userAccount = UserAccount.builder()
                .fullName(userDto.getFullName())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .archived(false)
                .accountBalance(0.0)
                .createdAt(LocalDateTime.now())
                .build();

        UserAccount userAccount1 = userAccountDao.register(userAccount);

        SavingsPlan savingsPlan1 = SavingsPlan.builder()
                .title("Amsha Professional")
                .description("Advanced studies plan")
                .createdAt(LocalDateTime.now().plusHours(3))
                .archived(false)
                .userAccount(userAccount1)
                .currentAmount(0.0)
                .targetAmount(0.0)
                .build();

        SavingsPlan savingsPlan2 = SavingsPlan.builder()
                .title("Amsha Lock")
                .description("Target Savings")
                .createdAt(LocalDateTime.now().plusHours(3))
                .archived(false)
                .userAccount(userAccount1)
                .currentAmount(0.0)
                .targetAmount(0.0)
                .build();

        SavingsPlan savingsPlan3 = SavingsPlan.builder()
                .title("Amsha ESG ")
                .description("Amsha ESG")
                .createdAt(LocalDateTime.now().plusHours(3))
                .archived(false)
                .userAccount(userAccount1)
                .currentAmount(0.0)
                .targetAmount(0.0)
                .build();

        userAccount1.setSavingsPlans(new ArrayList<>());
        userAccount1.getSavingsPlans().add(savingsPlan1);
        userAccount1.getSavingsPlans().add(savingsPlan2);
        userAccount1.getSavingsPlans().add(savingsPlan3);


        UserAccount updatedUser = userAccountDao.updateUser(userAccount1);

        return userAccountMapper.toUserDto(updatedUser);
    }

    @Override
    public UserLoginResponseDto login(String email, String token) {
        UserAccount userAccount = userAccountDao.getUserByEmail(email);
        return userAccountMapper.toUserLoginResponseDto(userAccount, token);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserUpdateDto userDto) {
        UserAccount userAccount = userAccountDao.getUserById(userDto.getId());
        userAccount.setFullName(userDto.getFullName());
        userAccount.setPhoneNumber(userDto.getPhoneNumber());
        userAccount.setEmail(userDto.getEmail());
        userAccount.setPassword(userDto.getPassword());
        userAccount.setRole(userDto.getRole());
        userAccount.setLastUpdate(LocalDateTime.now());
        return userAccountMapper.toUserDto(userAccountDao.updateUser(userAccount));
    }
    @Transactional
    @Override
    public UserDto setUserProfilePic(Integer userId, String bucketName, MultipartFile file) throws IOException {

        UserAccount userAccount = userAccountDao.getUserById(userId);

        String fileName = awsService.uploadFile(bucketName, file);
        userAccount.setImageName(fileName);
        String filePath = awsService.getFileUrl(bucketName, fileName);

        userAccount.setUserProfilePic(filePath);

        return userAccountMapper.toUserDto(userAccountDao.updateUser(userAccount));
    }
    @Transactional
    @Override
    public String deleteProfilePic(Integer userId, String bucketName) {
        UserAccount userAccount = userAccountDao.getUserById(userId);
        awsService.deleteFile(bucketName, userAccount.getImageName());
        userAccount.setImageName(null);
        userAccount.setUserProfilePic(null);
        userAccountDao.updateUser(userAccount);
        return "User profile pic deleted";
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userAccountMapper.toUserDto(userAccountDao.getUserById(userId));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userAccountMapper.toUserDto(userAccountDao.getUserByEmail(email));
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        return userAccountMapper.toUserDto(userAccountDao.getUserByPhoneNumber(phoneNumber));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserAccount> userAccounts = userAccountDao.getAllUsers();
        for(UserAccount userAccount : userAccounts) {
            userDtos.add(userAccountMapper.toUserDto(userAccount));
        }
        return userDtos;
    }

    @Override
    public UserDto archiveUser(Integer userId) {
        UserAccount userAccount = userAccountDao.getUserById(userId);
        userAccount.setArchived(true);
        userAccountDao.archiveUser(userAccount);
        return userAccountMapper.toUserDto(userAccount);
    }

    @Override
    public UserDto changePassword(UserPasswordDto userPasswordDto) throws Exception {
        try {
            UserAccount userAccount = userAccountDao.getUserByEmail(userPasswordDto.getEmail());
            userAccount.setPassword(passwordEncoder.encode(userPasswordDto.getNewPassword()));
            return userAccountMapper.toUserDto(userAccountDao.updateUser(userAccount));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public UserWalletDto getUserWallet(Integer userId) {
        return userAccountMapper.toUserWalletDto(userAccountDao.getUserById(userId));
    }
}
