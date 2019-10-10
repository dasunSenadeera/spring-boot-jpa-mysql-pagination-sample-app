package com.example.challenge;

import com.example.challenge.enums.TicketPriority;
import com.example.challenge.enums.TicketStatus;
import com.example.challenge.enums.TicketType;
import com.example.challenge.enums.TicketVia;
import com.example.challenge.interfaces.UserDetails;
import com.example.challenge.models.Organization;
import com.example.challenge.models.Ticket;
import com.example.challenge.models.User;
import com.example.challenge.service.OrganizationService;
import com.example.challenge.service.TicketService;
import com.example.challenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	TicketService ticketService;

//	public static void main(String[] args) {
//		SpringApplication.run(ChallengeApplication.class, args);
//	}

	public static void main(String[] args) throws Exception {

		//disabled banner, don't want to see the spring logo
		SpringApplication app = new SpringApplication(ChallengeApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Organization>> typeReference = new TypeReference<List<Organization>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/organizations.json");
		try {
			List<Organization> organizations = mapper.readValue(inputStream,typeReference);
			List<Organization> organizationList = organizationService.saveInitialOrganizations(organizations);
			log.info("Organization details added to database successfully");
		} catch (IOException e){
			e.printStackTrace();
			log.error("Organization details adding failed");
		}

		TypeReference<List<User>> typeReference_user = new TypeReference<List<User>>(){};
		InputStream inputStream_user = TypeReference.class.getResourceAsStream("/json/users.json");
		try {
			List<User> users = mapper.readValue(inputStream_user,typeReference_user);
			List<User> userList = userService.saveInitialUsers(users);
			log.info("User details added to database successfully");
		} catch (IOException e){
			System.out.println(e.toString());
			log.error("User details adding failed");
		}

		TypeReference<List<Ticket>> typeReference_ticket = new TypeReference<List<Ticket>>(){};
		InputStream inputStream_ticket = TypeReference.class.getResourceAsStream("/json/tickets.json");
		try {
			List<Ticket> tickets = mapper.readValue(inputStream_ticket,typeReference_ticket);
			this.ticketService.saveInitialTickets(tickets);
			log.info("Ticket details added to database successfully");
		} catch (IOException e){
			System.out.println(e.toString());
			log.error("Tickets details adding failed");
		}

		Scanner keyboard = new Scanner(System.in);

		int option = 0;
		while (option != 3)
		{
			option = showMenu();
			switch (option) {
				case 1:
					int  option_one = 0;
					while (option_one != 4){
						option_one = showMainOption1();
						switch (option_one){
							case 1:
								int option_users_main = 0;
								while (option_users_main != 16){
									option_users_main = showUserSearchOptions();
									switch (option_users_main){
										case 1:
											System.out.println("\n\t\tFind user by given _id");
											System.out.print("\t\tPress _id of the user ");
											int option_user_id = keyboard.nextInt();
											Optional<User> user_by_given_id = userService.getUser(Long.valueOf(option_user_id));
											if(user_by_given_id.isPresent()){
												System.out.println("\t\t" + user_by_given_id);
											}else{
												System.out.println("\t\tUser Not Found with given _id : " + option_user_id);
											}
											break;
										case 2:
											System.out.println("\n\t\tFind user by given name");
											System.out.print("\t\tPress name of the user");
											String option_user_name = keyboard.nextLine();
											Optional<User> user_by_given_name = userService.getUserByName(option_user_name);
											if(user_by_given_name.isPresent()){
												System.out.println("\t\t" + user_by_given_name);
											}else{
												System.out.println("\t\tUser Not Found with given name : " + option_user_name);
											}
											break;
										case 3:
											System.out.println("\n\t\tFind user by given url");
											System.out.print("\t\tPress url of the user : ");
											String option_user_url = keyboard.nextLine();
											Optional<User> user_by_given_url = userService.findUserByUrl(option_user_url);
											if(user_by_given_url.isPresent()){
												System.out.println("\t\t" + user_by_given_url);
											}else{
												System.out.println("\t\tUser Not Found with given url : " + option_user_url);
											}
											break;
										case 4:
											System.out.println("\n\t\tFind user by given external_id");
											System.out.print("\t\tPress external_id of the user : ");
											String option_user_external_id = keyboard.nextLine();
											Optional<User> user_by_given_external_id = userService.findUserByExternalId(option_user_external_id);
											if(user_by_given_external_id.isPresent()){
												System.out.println("\t\t" + user_by_given_external_id);
											}else{
												System.out.println("\t\tUser Not Found with given external_id : " + option_user_external_id);
											}
											break;
										case 5:
											System.out.println("\n\t\tFind user by given email");
											System.out.print("\t\tPress email of the user : ");
											String option_user_email = keyboard.nextLine();
											Optional<User> user_by_given_email = userService.findUserByEmail(option_user_email);
											if(user_by_given_email.isPresent()){
												System.out.println("\t\t" + user_by_given_email);
											}else{
												System.out.println("\t\tUser Not Found with given email : " + option_user_email);
											}
											break;
										case 6:
											System.out.println("\n\t\tFind user by given phone");
											System.out.print("\t\tPress phone of the user : ");
											String option_user_phone = keyboard.nextLine();
											Optional<User> user_by_given_phone = userService.findUserByPhone(option_user_phone);
											if(user_by_given_phone.isPresent()){
												System.out.println("\t\t" + user_by_given_phone);
											}else{
												System.out.println("\t\tUser Not Found with given phone : " + option_user_phone);
											}
											break;
										case 7:
											System.out.println("\n\t\tFind user by active");
											System.out.print("\t\tPress active of the user(True = 1, False =2) : ");
											int option_user_active = keyboard.nextInt();
											boolean user_active= false;

											if(option_user_active == 1 || option_user_active == 2){
												if(option_user_active == 1)
												user_active = true;

												System.out.print("\t\tPress page number : ");
												int option_user_active_page = keyboard.nextInt();
												System.out.print("\t\tPress page size : ");
												int option_user_active_size = keyboard.nextInt();
												Page<User> userServiceByActive = userService.findByActive(user_active, option_user_active_page, option_user_active_size);
												System.out.println("\t\tTotal Number of Elements : " +userServiceByActive.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +userServiceByActive.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +userServiceByActive.getNumberOfElements());
												System.out.println("\t\tUsers with Active value of : " + user_active + " with page : " + option_user_active_page +  " and size :" + option_user_active_size);
												userServiceByActive.getContent().forEach(user -> {
													System.out.println("\t\t" + user);
												});
											} else {
												System.out.println("\t\tInvalid Input");
											}
											break;
										case 8:
											System.out.println("\n\t\tFind user by verified");
											System.out.print("\t\tPress verified of the user(True = 1, False =2) : ");
											int option_user_verified = keyboard.nextInt();
											boolean user_verified= false;

											if(option_user_verified == 1 || option_user_verified == 2){
												if(option_user_verified == 1)
													user_verified = true;

												System.out.print("\t\tPress page number : ");
												int option_user_verified_page = keyboard.nextInt();
												System.out.print("\t\tPress page size : ");
												int option_user_verified_size = keyboard.nextInt();
												Page<User> user_verifiedPage = userService.findByVerified(user_verified, option_user_verified_page, option_user_verified_size);
												System.out.println("\t\tTotal Number of Elements : " +user_verifiedPage.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +user_verifiedPage.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +user_verifiedPage.getNumberOfElements());
												System.out.println("\t\tUsers with Verified value of : " + user_verified + " with page : " + option_user_verified_page +  " and size :" + option_user_verified_size);
												user_verifiedPage.getContent().forEach(user -> {
													System.out.println("\t\t" + user);
												});
											} else {
												System.out.println("\t\tInvalid Input");
											}
											break;
										case 9:
											System.out.println("\n\t\tFind user by shared");
											System.out.print("\t\tPress shared of the user(True = 1, False =2) : ");
											int option_user_shared = keyboard.nextInt();
											boolean user_shared= false;

											if(option_user_shared == 1 || option_user_shared == 2){
												if(option_user_shared == 1)
													user_shared = true;

												System.out.print("\t\tPress page number : ");
												int option_user_shared_page = keyboard.nextInt();
												System.out.print("\t\tPress page size : ");
												int option_user_shared_size = keyboard.nextInt();
												Page<User> userServiceByShared = userService.findByShared(user_shared, option_user_shared_page, option_user_shared_size);
												System.out.println("\t\tTotal Number of Elements : " +userServiceByShared.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +userServiceByShared.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +userServiceByShared.getNumberOfElements());
												System.out.println("\t\tUsers with Shared value of : " + user_shared + " with page : " + option_user_shared_page +  " and size :" + option_user_shared_size);
												userServiceByShared.getContent().forEach(user -> {
													System.out.println("\t\t" + user);
												});
											} else {
												System.out.println("\t\tInvalid Input");
											}
											break;
										case 10:
											System.out.println("\n\t\tFind user by suspended");
											System.out.print("\t\tPress suspended of the user(True = 1, False =2) : ");
											int option_user_suspended = keyboard.nextInt();
											boolean user_suspended= false;

											if(option_user_suspended == 1 || option_user_suspended == 2){
												if(option_user_suspended == 1)
													user_suspended = true;

												System.out.print("\t\tPress page number : ");
												int option_user_suspended_page = keyboard.nextInt();
												System.out.print("\t\tPress page size : ");
												int option_user_suspended_size = keyboard.nextInt();
												Page<User> userServiceBySuspended = userService.findBySuspended(user_suspended, option_user_suspended_page, option_user_suspended_size);
												System.out.println("\t\tTotal Number of Elements : " +userServiceBySuspended.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +userServiceBySuspended.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +userServiceBySuspended.getNumberOfElements());
												System.out.println("\t\tUsers with Suspended value of : " + user_suspended + " with page : " + option_user_suspended_page +  " and size :" + option_user_suspended_size);
												userServiceBySuspended.getContent().forEach(user -> {
													System.out.println("\t\t" + user);
												});
											} else {
												System.out.println("\t\tInvalid Input");
											}
											break;
										case 11:
											System.out.println("\n\t\tFind user by role");
											System.out.print("\t\tRole Name : ");
											String option_user_role = keyboard.next();
											System.out.print("\t\tPress page number : ");
											int option_user_role_page = keyboard.nextInt();
											System.out.print("\t\tPress page size : ");
											int option_user_role_size = keyboard.nextInt();
											Page<User> userServiceByRole = userService.findByRole(option_user_role, option_user_role_page, option_user_role_size);
											System.out.println("\t\tTotal Number of Elements : " +userServiceByRole.getTotalElements());
											System.out.println("\t\tTotal Number of Pages : " +userServiceByRole.getTotalPages());
											System.out.println("\t\tTotal Number of Elements in this page: " +userServiceByRole.getNumberOfElements());
											System.out.println("\t\tUsers with Role value of : " + option_user_role + " with page : " + option_user_role_page +  " and size :" + option_user_role_size);
											userServiceByRole.getContent().forEach(user -> {
												System.out.println("\t\t" + user);
											});
											break;
										case 12:
											System.out.println("\n\t\tFind users by given tag list with Pagination");
											System.out.print("\t\tEnter tags with comma separated values : ");
											String option_user_tags = keyboard.next();
											List<String> option_user_tag_list = Arrays.asList(option_user_tags.split("\\s*,\\s*"));
											System.out.print("\t\tPress page number : ");
											int option_user_tags_page = keyboard.nextInt();
											System.out.print("\t\tPress page size : ");
											int option_user_tags_size = keyboard.nextInt();
											Page<User> serviceTicketsByTagNames= userService.findTicketsByTagNames(option_user_tag_list, option_user_tags_page, option_user_tags_size);
											System.out.println("\t\tTotal Number of Elements : " +serviceTicketsByTagNames.getTotalElements());
											System.out.println("\t\tTotal Number of Pages : " +serviceTicketsByTagNames.getTotalPages());
											System.out.println("\t\tTotal Number of Elements in this page: " +serviceTicketsByTagNames.getNumberOfElements());
											System.out.println("\t\tFind users by given tag list with Pagination  with page : " + option_user_tags_page +  " and size :" + option_user_tags_size);
											serviceTicketsByTagNames.getContent().forEach(user -> System.out.println("\t\t" + user));
											break;
										case 13:
											System.out.println("\n\t\tFind all Users with Pagination");
											System.out.print("\t\tPress page number : ");
											int option_all_users_role_page = keyboard.nextInt();
											System.out.print("\t\tPress page size : ");
											int option_all_users_role_size = keyboard.nextInt();
											Page<User> userServiceAllWithPagination = userService.findAllWithPagination(option_all_users_role_page, option_all_users_role_size);
											System.out.println("\t\tTotal Number of Elements : " +userServiceAllWithPagination.getTotalElements());
											System.out.println("\t\tTotal Number of Pages : " +userServiceAllWithPagination.getTotalPages());
											System.out.println("\t\tTotal Number of Elements in this page: " +userServiceAllWithPagination.getNumberOfElements());
											System.out.println("\t\tAll users with pagination  with page : " + option_all_users_role_page +  " and size :" + option_all_users_role_size);
											userServiceAllWithPagination.getContent().forEach(user -> {
												System.out.println("\t\t" + user);
											});
											break;
										case 14:
											System.out.println("\n\t\tAll Users");
											userService.findAll().forEach(user -> System.out.println("\t\t" +user));
											break;
										case 15:
											System.out.println("\n\t\tFind Users By Locale");
											System.out.print("\t\tLocale (en-AU  / de-CH / zh-CN): ");
											String option_user_locale = keyboard.next();
											Locale locale = new Locale.Builder().setLanguageTag(option_user_locale).build();
											System.out.print("\t\tPress page number : ");
											int option_locale_page = keyboard.nextInt();
											System.out.print("\t\tPress page size : ");
											int option_locale_size = keyboard.nextInt();

											Page<User> findUsersByLocaleWithPagination = userService.findByLocale(locale, option_locale_page, option_locale_size);
											System.out.println("\t\tTotal Number of Elements : " +findUsersByLocaleWithPagination.getTotalElements());
											System.out.println("\t\tTotal Number of Pages : " +findUsersByLocaleWithPagination.getTotalPages());
											System.out.println("\t\tTotal Number of Elements in this page: " +findUsersByLocaleWithPagination.getNumberOfElements());
											System.out.println("\t\tAll users by Locale with pagination  with page : " + option_user_locale +  " and size :" + option_locale_size);
											findUsersByLocaleWithPagination.getContent().forEach(user -> System.out.println("\t\t" +user));
											System.out.println("\n\n\n");
											break;
										default:
											System.out.println("\t\tSorry, please enter valid Option");
									}
									option_users_main= 16;
								}
								break;
							case 2:
								int option_tickets_main = 0;
								while (option_tickets_main !=21){
									option_tickets_main = showTicketSearchOptions();
									switch (option_tickets_main){
										case 1:
											System.out.println("\n\t\tFind Ticket By Given _id");
											System.out.print("\t\tType _id of the ticket ");
											String option_ticket_id = keyboard.next();
											Optional<Ticket> ticket_by_given_id = ticketService.getTicket(option_ticket_id);
											if(ticket_by_given_id.isPresent()){
												System.out.println("\t\t" + ticket_by_given_id);
											}else{
												System.out.println("\t\tUser Not Found with given _id : " + ticket_by_given_id);
											}
											break;
										case 2:
											System.out.println("\n\t\tFind all Tickets with Pagination");
											System.out.print("\t\tPress page number : ");
											int option_all_ticket_role_page = keyboard.nextInt();
											System.out.print("\t\tPress page size : ");
											int option_all_ticket_role_size = keyboard.nextInt();
											Page<Ticket> ticketServiceAll = ticketService.findAll(option_all_ticket_role_page, option_all_ticket_role_size);
											System.out.println("\t\tTotal Number of Elements : " +ticketServiceAll.getTotalElements());
											System.out.println("\t\tTotal Number of Pages : " +ticketServiceAll.getTotalPages());
											System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceAll.getNumberOfElements());
											System.out.println("\t\tAll Tickets with pagination  with page : " + option_all_ticket_role_page +  " and size :" + option_all_ticket_role_size);
											ticketServiceAll.getContent().forEach(user -> {
												System.out.println("\t\t" + user);
											});
											break;
										case 3:
											System.out.println("\n\t\tAll Tickets");
											ticketService.findAll().forEach(ticket -> System.out.println("\t\t" + ticket));
											break;
										case 4:
											System.out.println("\n\t\tFind ticket by given url");
											System.out.print("\t\tPress url of the ticket : ");
											String option_ticket_url = keyboard.nextLine();
											Optional<Ticket> ticket_by_given_url = ticketService.findByUrl(option_ticket_url);
											if(ticket_by_given_url.isPresent()){
												System.out.println("\t\t" + ticket_by_given_url);
											}else{
												System.out.println("\t\tTicket Not Found with given url : " + option_ticket_url);
											}
											break;
										case 5:
											System.out.println("\n\t\tFind ticket by given external_id");
											System.out.print("\t\tType external_id of the ticket : ");
											String option_ticket_external_id = keyboard.nextLine();
											Optional<Ticket> ticket_by_given_external_id = ticketService.findByExternalID(option_ticket_external_id);
											if(ticket_by_given_external_id.isPresent()){
												System.out.println("\t\t" + ticket_by_given_external_id);
											}else{
												System.out.println("\t\tUser Not Found with given external_id : " + option_ticket_external_id);
											}
											break;
										case 6:
                                            System.out.println("\n\t\tFind All Tickets By Created by with given date");
                                            System.out.print("\t\tType created at date of the ticket :(ex:2016-01-13T05:42:04 -11:00) ");
                                            String option_ticket_createdBy = keyboard.nextLine();

                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
                                            LocalDateTime formatDateTime = LocalDateTime.parse(option_ticket_createdBy, formatter);
                                            ticketService.findAllByCreatedAt(formatDateTime).forEach(ticket -> System.out.println("\t\t"+ ticket));

											break;
										case 7:
                                            System.out.println("\n\t\tFind List of Tickets Before Created By");
                                            System.out.print("\t\tType created at date of the ticket :(ex:2016-01-13T05:42:04 -11:00) ");
                                            String option_ticket_createdBy_before = keyboard.nextLine();

                                            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
                                            LocalDateTime formatDateTime2 = LocalDateTime.parse(option_ticket_createdBy_before, formatter2);
                                            ticketService.findAllByBeforeCreatedAt(formatDateTime2).forEach(ticket -> System.out.println("\t\t"+ ticket));
											break;
										case 8:
                                            System.out.println("\n\t\tFind List of Tickets After Created By");
                                            System.out.print("\t\tType created at after date of the ticket :(ex:2016-01-13T05:42:04 -11:00) ");
                                            String option_ticket_createdBy_after = keyboard.nextLine();

                                            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
                                            LocalDateTime formatDateTime3 = LocalDateTime.parse(option_ticket_createdBy_after, formatter3);
                                            ticketService.findAllByBeforeCreatedAt(formatDateTime3).forEach(ticket -> System.out.println("\t\t"+ ticket));
                                            break;
										case 9:
                                            System.out.println("\n\t\tFind List of Tickets Created At between two dates");
                                            System.out.print("\t\tType created at before date of the ticket :(ex:2016-01-13T05:42:04 -11:00) ");
                                            String option_ticket_createdBy_inbetween_before = keyboard.nextLine();
                                            System.out.print("\t\tType created at before date of the ticket :(ex:2016-01-13T05:42:04 -11:00) ");
                                            String option_ticket_createdBy_inbetween_after = keyboard.nextLine();


                                            DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
                                            LocalDateTime ticket_start = LocalDateTime.parse(option_ticket_createdBy_inbetween_before, formatter4);
                                            LocalDateTime ticket_end = LocalDateTime.parse(option_ticket_createdBy_inbetween_after, formatter4);
                                            ticketService.findAllTicketsCreatedBetween(ticket_start, ticket_end).forEach(ticket -> System.out.println(ticket));

											break;
										case 10:
											System.out.println("\n\t\tFind All Tickets by Ticket Type(INCIDENT/PROBLEM/QUESTION/TASK) with Pagination");
											System.out.print("\t\tType Ticket Type(INCIDENT = 1,PROBLEM = 2, QUESTION = 3, TASK = 4) : ");
											int option_ticket_type = keyboard.nextInt();
											TicketType ticketType = null;
											if( option_ticket_type == 1 || option_ticket_type == 2 || option_ticket_type == 3 || option_ticket_type ==4 ){
												switch (option_ticket_type){
													case 1: ticketType= TicketType.INCIDENT;
													break;
													case 2: ticketType= TicketType.PROBLEM;
													break;
													case 3: ticketType= TicketType.QUESTION;
													break;
													case 4: ticketType= TicketType.TASK;
												}

												System.out.print("\t\tPress page number : ");
												int option_ticket_type_page = keyboard.nextInt();
												System.out.print("\t\tPress page number : ");
												int option_ticket_type_size = keyboard.nextInt();

												Page<Ticket> ticketServiceByTicketType= ticketService.findByTicketType(ticketType, option_ticket_type_page, option_ticket_type_size);
												System.out.println("\t\tTotal Number of Elements : " +ticketServiceByTicketType.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +ticketServiceByTicketType.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceByTicketType.getNumberOfElements());
												System.out.println("\t\tAll users with pagination  with page : " + option_ticket_type_page +  " and size :" + option_ticket_type_size);
												ticketServiceByTicketType.getContent().forEach(ticket -> {
													System.out.println("\t\t" + ticket);
												});



											} else{
												System.out.println("\t\tInvalid input");
											}


											break;
										case 11:
											System.out.println("\n\t\tFind All Tickets by Subject with Pagination");
											System.out.print("\t\tType subject of the ticket");
											String option_ticket_subject = keyboard.nextLine();
											System.out.print("\t\tPress page number : ");
											int option_ticket_subject_page = keyboard.nextInt();
											System.out.print("\t\tPress page number : ");
											int option_ticket_subject_size = keyboard.nextInt();

											Page<Ticket> ticketServiceBySubject= ticketService.findBySubject(option_ticket_subject, option_ticket_subject_page, option_ticket_subject_size);
											System.out.println("\t\tTotal Number of Elements : " +ticketServiceBySubject.getTotalElements());
											System.out.println("\t\tTotal Number of Pages : " +ticketServiceBySubject.getTotalPages());
											System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceBySubject.getNumberOfElements());
											System.out.println("\t\tAll users with pagination  with page : " + option_ticket_subject_page +  " and size :" + option_ticket_subject_size);
											ticketServiceBySubject.getContent().forEach(ticket -> {
												System.out.println("\t\t" + ticket);
											});


											break;
										case 12:
											System.out.println("\n\t\tFind All Tickets With Priority(HIGH/LOW/NORMAL/URGENT) with Pagination");
											System.out.print("\t\tType Ticket Priority(HIGH = 1,LOW = 2, NORMAL = 3, URGENT = 4) : ");
											int option_ticket_priority = keyboard.nextInt();
											TicketPriority ticketPriority = null;
											if( option_ticket_priority == 1 || option_ticket_priority == 2 || option_ticket_priority == 3 || option_ticket_priority ==4 ){
												switch (option_ticket_priority){
													case 1: ticketPriority= TicketPriority.HIGH;
														break;
													case 2: ticketPriority= TicketPriority.LOW;
														break;
													case 3: ticketPriority= TicketPriority.NORMAL;
														break;
													case 4: ticketPriority= TicketPriority.URGENT;
												}

												System.out.print("\t\tPress page number : ");
												int option_ticket_priority_page = keyboard.nextInt();
												System.out.print("\t\tPress page number : ");
												int option_ticket_priority_size = keyboard.nextInt();

												Page<Ticket> ticketServiceByTicketPriority= ticketService.findByTicketPriority(ticketPriority, option_ticket_priority_page, option_ticket_priority_size);
												System.out.println("\t\tTotal Number of Elements : " +ticketServiceByTicketPriority.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +ticketServiceByTicketPriority.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceByTicketPriority.getNumberOfElements());
												System.out.println("\t\tFind By Ticket Priority with pagination  with page : " + option_ticket_priority_page +  " and size :" + option_ticket_priority_size);
												ticketServiceByTicketPriority.getContent().forEach(ticket -> {
													System.out.println("\t\t" + ticket);
												});



											} else{
												System.out.println("\t\tInvalid input");
											}


											break;
										case 13:
											System.out.println("\n\t\tFind All Tickets by status(PENDING/HOLD/CLOSED/SOLVED/OPEN) with pagentination");
											System.out.print("\t\tType Ticket Status(PENDING = 1,HOLD = 2, CLOSED = 3, SOLVED = 4, OPEN =5) : ");
											int option_ticket_status = keyboard.nextInt();
											TicketStatus ticketStatus = null;
											if( option_ticket_status == 1 || option_ticket_status == 2 || option_ticket_status == 3 || option_ticket_status ==4 ){
												switch (option_ticket_status){
													case 1: ticketStatus= TicketStatus.PENDING;
														break;
													case 2: ticketStatus= TicketStatus.HOLD;
														break;
													case 3: ticketStatus= TicketStatus.CLOSED;
														break;
													case 4: ticketStatus= TicketStatus.SOLVED;
													break;
													case 5: ticketStatus= TicketStatus.OPEN;
													break;

												}

												System.out.print("\t\tPress page number : ");
												int option_ticket_status_page = keyboard.nextInt();
												System.out.print("\t\tPress page number : ");
												int option_ticket_status_size = keyboard.nextInt();

												Page<Ticket> ticketServiceByTicketStatus= ticketService.findByTicketStatus(ticketStatus, option_ticket_status_page, option_ticket_status_size);
												System.out.println("\t\tTotal Number of Elements : " +ticketServiceByTicketStatus.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +ticketServiceByTicketStatus.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceByTicketStatus.getNumberOfElements());
												System.out.println("\t\tFind By Ticket Status with pagination  with page : " + option_ticket_status_page +  " and size :" + option_ticket_status_size);
												ticketServiceByTicketStatus.getContent().forEach(ticket -> {
													System.out.println("\t\t" + ticket);
												});



											} else{
												System.out.println("\t\tInvalid input");
											}


											break;
										case 14:
											System.out.println("\n\t\tFind Tickets by given tag list with Pagination");
											System.out.print("\t\tEnter tags with comma separated values : ");
											String option_ticket_tags = keyboard.next();
											List<String> option_ticket_tag_list = Arrays.asList(option_ticket_tags.split("\\s*,\\s*"));
											System.out.print("\t\tPress page number : ");
											int option_ticket_tags_page = keyboard.nextInt();
											System.out.print("\t\tPress page size : ");
											int option_ticket_tags_size = keyboard.nextInt();
											Page<Ticket>  ticketServiceTicketsByTagNames= ticketService.findTicketsByTagNames(option_ticket_tag_list, option_ticket_tags_page, option_ticket_tags_size);
											System.out.println("\t\tTotal Number of Elements : " +ticketServiceTicketsByTagNames.getTotalElements());
											System.out.println("\t\tTotal Number of Pages : " +ticketServiceTicketsByTagNames.getTotalPages());
											System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceTicketsByTagNames.getNumberOfElements());
											System.out.println("\t\tFind users by given tag list with Pagination  with page : " + option_ticket_tags_page +  " and size :" + option_ticket_tags_size);
											ticketServiceTicketsByTagNames.getContent().forEach(user -> System.out.println("\t\t" + user));
											break;
										case 15:
											System.out.println("\n\t\tFind Ticket list By HasIncidents with Pagination");
											System.out.print("\t\tPress HasIncidents of the Ticket(True = 1, False =2) : ");
											int option_ticket_has_incident = keyboard.nextInt();
											boolean ticket_has_incident= false;

											if(option_ticket_has_incident == 1 || option_ticket_has_incident == 2){
												if(option_ticket_has_incident == 1)
													ticket_has_incident = true;

												System.out.print("\t\tPress page number : ");
												int option_ticket_has_incident_page = keyboard.nextInt();
												System.out.print("\t\tPress page size : ");
												int option_ticket_has_incident_size = keyboard.nextInt();
												Page<Ticket> ticketServiceByHasIncidents = ticketService.findByHasIncidents(ticket_has_incident, option_ticket_has_incident_page, option_ticket_has_incident_size);
												System.out.println("\t\tTotal Number of Elements : " +ticketServiceByHasIncidents.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +ticketServiceByHasIncidents.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceByHasIncidents.getNumberOfElements());
												System.out.println("\t\tTickets with Has Incident value of : " + ticket_has_incident + " with page : " + option_ticket_has_incident_page +  " and size :" + option_ticket_has_incident_size);
												ticketServiceByHasIncidents.getContent().forEach(user -> {
													System.out.println("\t\t" + user);
												});
											} else {
												System.out.println("\t\tInvalid Input");
											}
											break;
										case 16:
											System.out.println("\n\t\tFind Ticket list By via(WEB/CHAT/VOICE) with Pagination");
											System.out.print("\t\tType Ticket Via(WEB = 1,CHAT = 2, VOICE = 3) : ");
											int option_ticket_via = keyboard.nextInt();
											TicketVia ticketVia = null;
											if( option_ticket_via == 1 || option_ticket_via == 2 || option_ticket_via == 3 ){
												switch (option_ticket_via){
													case 1: ticketVia= TicketVia.WEB;
														break;
													case 2: ticketVia= TicketVia.CHAT;
														break;
													case 3: ticketVia= TicketVia.VOICE;
														break;
												}

												System.out.print("\t\tPress page number : ");
												int option_ticket_via_page = keyboard.nextInt();
												System.out.print("\t\tPress page number : ");
												int option_ticket_via_size = keyboard.nextInt();

												Page<Ticket> ticketServiceByTicketVia= ticketService.findByTicketVia(ticketVia, option_ticket_via_page, option_ticket_via_size);
												System.out.println("\t\tTotal Number of Elements : " +ticketServiceByTicketVia.getTotalElements());
												System.out.println("\t\tTotal Number of Pages : " +ticketServiceByTicketVia.getTotalPages());
												System.out.println("\t\tTotal Number of Elements in this page: " +ticketServiceByTicketVia.getNumberOfElements());
												System.out.println("\t\tFind By Ticket Status with pagination  with page : " + option_ticket_via_page +  " and size :" + option_ticket_via_size);
												ticketServiceByTicketVia.getContent().forEach(ticket -> {
													System.out.println("\t\t" + ticket);
												});



											} else{
												System.out.println("\t\tInvalid input");
											}


											break;
										case 17:
											System.out.println("Sorry this has not implemented yet. This is same as 6) Find All Tickets By Created by with given date");
											break;
										case 18:
                                            System.out.println("Sorry this has not implemented yet. This is same as 7) Find List of Tickets Before Created By");
											break;
										case 19:
                                            System.out.println("Sorry this has not implemented yet. This is same as 8) Find List of Tickets After Created By");
											break;
										case 20:
                                            System.out.println("Sorry this has not implemented yet. This is same as 9) Find List of Tickets Created At between two dates");
											break;
										default:
											System.out.println("\t\tSorry, please enter valid Option");
									}
									option_tickets_main = 21;
								}
								break;
							case 3:
								System.out.println("\t\tSorry it has not implemented yet. See com.example.challenge.service.OrganizationService for Organization Service and com.example.challenge.repository for OrganizationRepository");
								break;
							default:
								System.out.println("\t\tSorry, please enter valid Option");
						}
						option_one =4;
					}
					break;
				case 2:
					System.out.println("-----------------------------------------------------------------------------------");
					System.out.println("\t\tSearch users with");
					System.out.println("\t\t1)Find user by given _id ");
					System.out.println("\t\t2)Find user by given name ");
					System.out.println("\t\t3)Find user by given url");
					System.out.println("\t\t4)Find user by given external_id");
					System.out.println("\t\t5)Find user by given email");
					System.out.println("\t\t6)Find user by given phone");
					System.out.println("\t\t7)Find users by active(True,False) with Pagination");
					System.out.println("\t\t8)Find users by verified(True,False) with Pagination");
					System.out.println("\t\t9)Find users by shared(True,False) with Pagination");
					System.out.println("\t\t10)Find users by suspended(True,False) with Pagination");
					System.out.println("\t\t11)Find Users by role with Pagination");
					System.out.println("\t\t12)Find users by given tag list with Pagination");
					System.out.println("\t\t13)Find all Users with Pagination");
					System.out.println("\t\t14)Find All Users");
					System.out.println("\t\t15)Find Users By Locale");

					System.out.println("\n-----------------------------------------------------------------------------------");
					System.out.println("\t\tSearch Tickets with");
					System.out.println("\t\t1) Find Ticket By Given _id ");
					System.out.println("\t\t2) Find All Tickets With Pagination ");
					System.out.println("\t\t3) List down All Tickets");
					System.out.println("\t\t4) Get ticket by given url");
					System.out.println("\t\t5) Get ticket by given external Id");
					System.out.println("\t\t6) Find All Tickets By Created by with given date");
					System.out.println("\t\t7) Find List of Tickets Before Created By");
					System.out.println("\t\t8) Find List of Tickets After Created By");
					System.out.println("\t\t9) Find List of Tickets Created At between two dates");
					System.out.println("\t\t10) Find All Tickets by Ticket Type(INCIDENT/PROBLEM/QUESTION/TASK) with Pagination");
					System.out.println("\t\t11) Find All Tickets by Subject with Pagination");
					System.out.println("\t\t12) Find All Tickets With Priority(HIGH/LOW/NORMAL/URGENT) with Pagination");
					System.out.println("\t\t13) Find All Tickets by status(PENDING/HOLD/CLOSED/SOLVED/OPEN) with pagentination");
					System.out.println("\t\t14) Find Tickets by given tag list with Pagination");
					System.out.println("\t\t15) Find Ticket list By HasIncidents with Pagination");
					System.out.println("\t\t16) Find Ticket list By via(WEB/CHAT/VOICE) with Pagination");
					System.out.println("\t\t17) Find All Tickets By due at with given date");
					System.out.println("\t\t18) Find List of Tickets Before due at");
					System.out.println("\t\t19) Find List of Tickets After due at");
					System.out.println("\t\t20) Find List of Tickets due At between two dates");

                    System.out.println("\n-----------------------------------------------------------------------------------");
                    System.out.println("\t\tSearch Organizations with");
                    System.out.println("\t\t1) Find All Organizations with pagination ");
                    System.out.println("\t\t2) Find All Organizations ");
                    System.out.println("\t\t3) Find Organizatin By _id");
                    System.out.println("\t\t4) Find Organizatin By Name");
                    System.out.println("\t\t5) Find Organization list By list of domain names");
                    System.out.println("\t\t6) Find Organization By Url");
                    System.out.println("\t\t7) Find Organization By externalId");
                    System.out.println("\t\t8) Find Organization list By list of tags");
                    System.out.println("\t\t9) Find Pagentinated Organization List by sharedTickets boolean value");
                    System.out.println("\t\t10) Find List of Organizations by Detail");
                    System.out.println("\t\t11) Find All Organizations Created at By Given Date");
                    System.out.println("\t\t12) Find List of Organizations Before Created By");
                    System.out.println("\t\t13) Find List of Organizations After Created By");
                    System.out.println("\t\t14) Find List of Organizations Created By between");
					break;
				case 3:
					System.out.println("\t\tThank you. Good Bye.");
                    System.exit(1);
				default:
					System.out.println("\t\tSorry, please enter valid Option");
			}
		}
	}

	public static int showMenu() {
		int option = 0;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\n\n\t\tSelect Search options:");
		System.out.println("\t\tPress 1 to search");
		System.out.println("\t\tPress 2 to view a list of searchable fields");
		System.out.println("\t\tPress 3 to exit");
		System.out.print("\t\tEnter your choice : ");
		option = keyboard.nextInt();
		return option;
	}

	public static int showMainOption1() {
		int option = 0;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\n\n\t\tPress 1 to Users");
		System.out.println("\t\tPress 2 to Tickets");
		System.out.println("\t\tPress 3 to Organizations");
		System.out.println("\t\tPress 4 for go to main menu");
		System.out.print("\t\tEnter your choice : ");
		option = keyboard.nextInt();
		return option;
	}

	public static int showUserSearchOptions() {
		int option = 0;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\n\n\t\t                          Search Users");
		System.out.println("\t\tPress 1 to Find user by given _id ");
		System.out.println("\t\tPress 2 to Find user by given name ");
		System.out.println("\t\tPress 3 to Find user by given url");
		System.out.println("\t\tPress 4 to Find user by given external_id");
		System.out.println("\t\tPress 5 to Find user by given email");
		System.out.println("\t\tPress 6 to Find user by given phone");
		System.out.println("\t\tPress 7 to Find users by active(True,False) with Pagination");
		System.out.println("\t\tPress 8 to Find users by verified(True,False) with Pagination");
		System.out.println("\t\tPress 9 to Find users by shared(True,False) with Pagination");
		System.out.println("\t\tPress 10 to Find users by suspended(True,False) with Pagination");
		System.out.println("\t\tPress 11 to Find Users by role with Pagination");
		System.out.println("\t\tPress 12 to Find users by given tag list with Pagination");
		System.out.println("\t\tPress 13 to Find all Users with Pagination");
		System.out.println("\t\tPress 14 to Find All Users");
		System.out.println("\t\tPress 15 to Find Users By Locale");
		System.out.print("\t\tEnter your choice : ");
		option = keyboard.nextInt();
		return option;
	}

	public static int showTicketSearchOptions() {
		int option = 0;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\n\n\t\t                          Search Tickets");
		System.out.println("\t\t1) Find Ticket By Given _id ");
		System.out.println("\t\t2) Find All Tickets With Pagination ");
		System.out.println("\t\t3) List down All Tickets");
		System.out.println("\t\t4) Get ticket by given url");
		System.out.println("\t\t5) Get ticket by given external Id");
		System.out.println("\t\t6) Find All Tickets By Created by with given date");
		System.out.println("\t\t7) Find List of Tickets Before Created By");
		System.out.println("\t\t8) Find List of Tickets After Created By");
		System.out.println("\t\t9) Find List of Tickets Created At between two dates");
		System.out.println("\t\t10) Find All Tickets by Ticket Type(INCIDENT/PROBLEM/QUESTION/TASK) with Pagination");
		System.out.println("\t\t11) Find All Tickets by Subject with Pagination");
		System.out.println("\t\t12) Find All Tickets With Priority(HIGH/LOW/NORMAL/URGENT) with Pagination");
		System.out.println("\t\t13) Find All Tickets by status(PENDING/HOLD/CLOSED/SOLVED/OPEN) with pagentination");
		System.out.println("\t\t14) Find Tickets by given tag list with Pagination");
		System.out.println("\t\t15) Find Ticket list By HasIncidents with Pagination");
		System.out.println("\t\t16) Find Ticket list By via(WEB/CHAT/VOICE) with Pagination");
		System.out.println("\t\t17) Find All Tickets By due at with given date");
		System.out.println("\t\t18) Find List of Tickets Before due at");
		System.out.println("\t\t19) Find List of Tickets After due at");
		System.out.println("\t\t20) Find List of Tickets due At between two dates");
		System.out.print("\t\tEnter your choice : ");
		option = keyboard.nextInt();
		return option;
	}
}









//		*******************************************************************************************  Tickets ***************************************************************
//		System.out.println("***********************************************************************  Tickets ****************************************************************");
//		System.out.println("1) Find Ticket By Given Id : " + "436bf9b0-1147-4c0a-8439-6f79833bff5b");
//		System.out.println(ticketService.getTicket("436bf9b0-1147-4c0a-8439-6f79833bff5b"));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("2)Find All Tickets With Pagination Here with Page 2 and size 5");
//		Page<Ticket> findAllTicketsWithPagination = ticketService.findAll( 2, 5);
//		System.out.println("Total Elements : " + findAllTicketsWithPagination.getTotalElements());
//		System.out.println("Total Pages : " + findAllTicketsWithPagination.getTotalPages());
//		System.out.println("Number of Elements : " + findAllTicketsWithPagination.getNumberOfElements());
//		findAllTicketsWithPagination.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("3)List down All Tickets Details");
//		List<Ticket> allticketList = ticketService.findAll();
//		allticketList.forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("4) Get ticket by given url : http://initech.tokoin.io.com/api/v2/tickets/4cce7415-ef12-42b6-b7b5-fb00e24f9cc1.json");
//		System.out.println(ticketService.findByUrl("http://initech.tokoin.io.com/api/v2/tickets/4cce7415-ef12-42b6-b7b5-fb00e24f9cc1.json"));
//		System.out.println("\n\n\n");
//
//		System.out.println("5) Get ticket by given external Id : 43e8e3b2-676f-4635-b3b7-df7ce7be93dc");
//		System.out.println(ticketService.findByExternalID("43e8e3b2-676f-4635-b3b7-df7ce7be93dc"));
//		System.out.println("\n\n\n");
//
//
//
//		System.out.println("6) Find All Tickets By Given Date : 2016-01-13T05:42:04 -11:00" );
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime = LocalDateTime.parse("2016-01-13T05:42:04 -11:00", formatter);
//		ticketService.findAllByCreatedAt(formatDateTime).forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("7) Find List of Tickets Before Created By : 2016-04-27T05:42:04 -11:00" );
//		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime2 = LocalDateTime.parse("2016-04-27T05:42:04 -11:00", formatter2);
//		ticketService.findAllByBeforeCreatedAt(formatDateTime2).forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("8) Find List of Tickets Before Created By : 2016-04-27T05:42:04 -11:00" );
//		DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime3 = LocalDateTime.parse("2016-04-27T05:42:04 -11:00", formatter3);
//		ticketService.findAllByAfterCreatedAt(formatDateTime3).forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("9) Find List of Tickets  Created At between : 2016-04-27T05:42:04 -11:00 and 2016-06-10T04:24:09 -11:00" );
//		DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime ticket_start = LocalDateTime.parse("2016-04-27T05:42:04 -11:00", formatter4);
//		LocalDateTime ticket_end = LocalDateTime.parse("2016-06-10T04:24:09 -11:00", formatter4);
//		ticketService.findAllTicketsCreatedBetween(ticket_start, ticket_end).forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("10)Find All Tickets With Type of incident with Page 2 and size 3");
//		Page<Ticket> findByTicketTypeWithPagination = ticketService.findByTicketType(TicketType.INCIDENT, 2, 3);
//		System.out.println("Total Elements : " + findByTicketTypeWithPagination.getTotalElements());
//		System.out.println("Total Pages : " + findByTicketTypeWithPagination.getTotalPages());
//		System.out.println("Number of Elements : " + findByTicketTypeWithPagination.getNumberOfElements());
//		findByTicketTypeWithPagination.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("11)Find All Tickets With Subject of incident with Page 2 and size 3");
//		Page<Ticket> findBySubjectWithPagination = ticketService.findBySubject( "A Catastrophe in Pakistan", 0, 3);
//		System.out.println("Total Elements : " + findBySubjectWithPagination.getTotalElements());
//		System.out.println("Total Pages : " + findBySubjectWithPagination.getTotalPages());
//		System.out.println("Number of Elements : " + findBySubjectWithPagination.getNumberOfElements());
//		findBySubjectWithPagination.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("12)Find All Tickets With Priority of high with Page 2 and size 3");
//		Page<Ticket> findByTicketPriorityWithPagination = ticketService.findByTicketPriority(TicketPriority.HIGH, 2, 3);
//		System.out.println("Total Elements : " + findByTicketPriorityWithPagination.getTotalElements());
//		System.out.println("Total Pages : " + findByTicketPriorityWithPagination.getTotalPages());
//		System.out.println("Number of Elements : " + findByTicketPriorityWithPagination.getNumberOfElements());
//		findByTicketPriorityWithPagination.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("13)Find All Tickets With status of hold  with Page 2 and size 3");
//		Page<Ticket> findByTicketStatusWithPagination = ticketService.findByTicketStatus(TicketStatus.HOLD, 2, 3);
//		System.out.println("Total Elements : " + findByTicketStatusWithPagination.getTotalElements());
//		System.out.println("Total Pages : " + findByTicketStatusWithPagination.getTotalPages());
//		System.out.println("Number of Elements : " + findByTicketStatusWithPagination.getNumberOfElements());
//		findByTicketStatusWithPagination.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("14)Find Ticket list By list of tags");
//		Page<Ticket> ticketslistByTagsList = ticketService.findTicketsByTagNames(Arrays.asList("Iowa", "Alaska"), 0, 3);
//		System.out.println("Total Elements : " + ticketslistByTagsList.getTotalElements());
//		System.out.println("Total Pages : " + ticketslistByTagsList.getTotalPages());
//		System.out.println("Number of Elements : " + ticketslistByTagsList.getNumberOfElements());
//		ticketslistByTagsList.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("15)Find Ticket list By HasIncidents");
//		Page<Ticket> ticketslistByHasIncidents = ticketService.findByHasIncidents(true, 0, 3);
//		System.out.println("Total Elements : " + ticketslistByHasIncidents.getTotalElements());
//		System.out.println("Total Pages : " + ticketslistByHasIncidents.getTotalPages());
//		System.out.println("Number of Elements : " + ticketslistByHasIncidents.getNumberOfElements());
//		ticketslistByHasIncidents.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("16)Find Ticket list By via");
//		Page<Ticket> ticketslistByVia = ticketService.findByTicketVia(TicketVia.VOICE, 0, 5);
//		System.out.println("Total Elements : " + ticketslistByVia.getTotalElements());
//		System.out.println("Total Pages : " + ticketslistByVia.getTotalPages());
//		System.out.println("Number of Elements : " + ticketslistByVia.getNumberOfElements());
//		ticketslistByVia.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("17) Find All Tickets By Given Date : 2016-08-23T01:54:35 -10:00" );
//		DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime5 = LocalDateTime.parse("2016-08-23T01:54:35 -10:00", formatter5);
//		Page<Ticket> ticket_due_at= ticketService.findAllByDueAt(formatDateTime5, 0, 2);
//		System.out.println("Total Elements : " + ticket_due_at.getTotalElements());
//		System.out.println("Total Pages : " + ticket_due_at.getTotalPages());
//		System.out.println("Number of Elements : " + ticket_due_at.getNumberOfElements());
//		ticket_due_at.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("18) Find List of Tickets Before Created By : 2016-04-27T05:42:04 -11:00" );
//		DateTimeFormatter formatter6 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime6 = LocalDateTime.parse("2016-04-27T05:42:04 -11:00", formatter6);
//		ticketService.findAllByBeforeDueAt(formatDateTime6).forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("19) Find List of Tickets Before Created By : 2016-04-27T05:42:04 -11:00" );
//		DateTimeFormatter formatter7 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime7 = LocalDateTime.parse("2016-04-27T05:42:04 -11:00", formatter7);
//		ticketService.findAllByAfterDueAt(formatDateTime7).forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//		System.out.println("20) Find List of Tickets  Created At between : 2016-07-29T05:42:04 -11:00 and 2016-08-10T04:24:09 -11:00" );
//		DateTimeFormatter formatter8 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime ticket_start_due = LocalDateTime.parse("2016-07-29T05:42:04 -11:00", formatter8);
//		LocalDateTime ticket_end_due = LocalDateTime.parse("2016-08-29T04:24:09 -11:00", formatter8);
//		Page<Ticket> findAllDueBetWeen= ticketService.findAllOrganizationsDueBetween(ticket_start_due, ticket_end_due, 0, 5);
//		System.out.println("Total Elements : " + findAllDueBetWeen.getTotalElements());
//		System.out.println("Total Pages : " + findAllDueBetWeen.getTotalPages());
//		System.out.println("Number of Elements : " + findAllDueBetWeen.getNumberOfElements());
//		findAllDueBetWeen.getContent().forEach(ticket -> System.out.println(ticket));
//		System.out.println("\n\n\n");
//
//




//		*******************************************************************************************  Organization ***************************************************************

//		####################################
//		1)Find All Organizations with pagination
// 		Page<Organization> organizationPage = organizationService.findAllOrganizationsWithPagination( 4, 4);
//		System.out.println("Total Elements : " + organizationPage.getTotalElements());
//		System.out.println("Total Pages : " + organizationPage.getTotalPages());
//		System.out.println("Number of Elements : " + organizationPage.getNumberOfElements());
//		System.out.println("Organizations List of page "  + organizationPage.getNumber() + " with size " + organizationPage.getNumberOfElements());
//		organizationPage.getContent().forEach(organization -> System.out.println(organization));


//		2)Find All Organizations with pagination
// 		List<Organization> organizationList = organizationService.findAll();
//		organizationList.forEach(organization -> System.out.println(organization));

//		#####################
//		3)Find Organizatin By _id
//		System.out.println(organizationService.getOrganization(101l).getCreatedAt());


//		#####################
//		4)Find Organizatin By Name
//		System.out.println(organizationService.getOrganizationsByName("Enthaze"));

//		#####################
//		5)Find Organization list By list of domain names
//		List<Organization> organizationList = organizationService.findOrganizationByDomainName(Arrays.asList("kage.com", "comvex.com"));
//		organizationList.forEach(organization -> {
//			System.out.println(organization.get_id());});

//		#######################
//		6)Find Organization By Url
//		System.out.println(organizationService.findOrganizationByUrl("http://initech.tokoin.io.com/api/v2/organizations/103.json"));

//		######################
//		7)Find Organization By externalId
//		System.out.println(organizationService.findOrganizationByExternalId("e73240f3-8ecf-411d-ad0d-80ca8a84053d"));


//		#####################
//		8)Find Organization list By list of tags
//		List<Organization> organizationList = organizationService.findOrganizationByTagNames(Arrays.asList("Parrish"));
//		organizationList.forEach(organization -> {
//			System.out.println(organization.get_id());});

//		#####################
//		9)Find Pagentinated Organization List by sharedTickets boolean value
//		Page<Organization> organizationPage = organizationService.findOrganizationBySharedTickets(true, 4, 2);
//		System.out.println("Total Elements : " + organizationPage.getTotalElements());
//		System.out.println("Total Pages : " + organizationPage.getTotalPages());
//		System.out.println("Number of Elements : " + organizationPage.getNumberOfElements());
//		System.out.println("Organizations List of page "  + organizationPage.getNumber() + " with size " + organizationPage.getNumberOfElements());
//		organizationPage.getContent().forEach(organization -> System.out.println(organization));


//		####################
//		10)Find List of Organizations by Detail
//		organizationService.findOrganizationsByDetail("MegaCorp").forEach(organization -> System.out.println(organization));

//		####################
////		11) Find All Organizations By Given Date
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime = LocalDateTime.parse("2016-02-11T04:24:09 -11:00", formatter);
//		System.out.println(formatDateTime);
//		organizationService.findAllByCreatedAt(formatDateTime).forEach(organization -> System.out.println(organization));

//		####################
//		12)Find List of Organizations Before Created By
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime = LocalDateTime.parse("2016-02-11T04:24:09 -11:00", formatter);
//		System.out.println(formatDateTime);
//		organizationService.findAllByBeforeCreatedAt(formatDateTime).forEach(organization -> System.out.println(organization));


//		####################
//		13)Find List of Organizations Before Created By
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime formatDateTime = LocalDateTime.parse("2016-02-11T04:24:09 -11:00", formatter);
//		System.out.println(formatDateTime);
//		organizationService.findAllByAfterCreatedAt(formatDateTime).forEach(organization -> System.out.println(organization));

//		####################
//		14)Find List of Organizations  Created At between
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
//		LocalDateTime start = LocalDateTime.parse("2016-02-11T04:24:09 -11:00", formatter);
//		LocalDateTime end = LocalDateTime.parse("2016-06-10T04:24:09 -11:00", formatter);
//		organizationService.findAllOrganizationsCreatedBetween(start, end).forEach(organization -> System.out.println(organization));

//		System.out.println("Search user by name :");
//
//		try {
//			Long id = sc.nextLong();
//			System.out.println(" User id : " + id);
//			System.out.println(usrService.getUser(id));
//		} catch ( InputMismatchException ex){
//			System.out.println("Invalid Input");
//		}


//
////		*******************************************************************************************  Users ***************************************************************
//		System.out.println("***********************************************************************  Users ****************************************************************");
//		System.out.println("1) Find User By Given _id : " + "2");
//		System.out.println(userService.getUser(2l));
//		System.out.println("\n\n\n");
//
//		System.out.println("2) Find User By Given Name : " + "Cross Barlow");
//		System.out.println(userService.getUserByName("Cross Barlow"));
//		System.out.println("\n\n\n");
//
//		System.out.println("3) Find User By Given url : " + "http://initech.tokoin.io.com/api/v2/users/2.json");
//		System.out.println(userService.findUserByUrl("http://initech.tokoin.io.com/api/v2/users/2.json"));
//		System.out.println("\n\n\n");
//
//		System.out.println("4) Find User By Given external id : " + "c9995ea4-ff72-46e0-ab77-dfe0ae1ef6c2");
//		System.out.println(userService.findUserByExternalId("c9995ea4-ff72-46e0-ab77-dfe0ae1ef6c2"));
//		System.out.println("\n\n\n");
//
//		System.out.println("5) Find User By Given email : " + "jonibarlow@flotonic.com");
//		System.out.println(userService.findUserByEmail("jonibarlow@flotonic.com"));
//		System.out.println("\n\n\n");
//
//		System.out.println("6) Find User By Given phone : " + "9575-552-585");
//		System.out.println(userService.findUserByPhone("9575-552-585"));
//		System.out.println("\n\n\n");
//
//		System.out.println("7)Find User By Active with pagination");
//		Page<User> userListByActive = userService.findByActive(true, 0, 3);
//		System.out.println("Total Elements : " + userListByActive.getTotalElements());
//		System.out.println("Total Pages : " + userListByActive.getTotalPages());
//		System.out.println("Number of Elements : " + userListByActive.getNumberOfElements());
//		userListByActive.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//		System.out.println("8)Find User By Verified with pagination");
//		Page<User> userListByVerified = userService.findByVerified(true, 0, 3);
//		System.out.println("Total Elements : " + userListByVerified.getTotalElements());
//		System.out.println("Total Pages : " + userListByVerified.getTotalPages());
//		System.out.println("Number of Elements : " + userListByVerified.getNumberOfElements());
//		userListByVerified.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//		System.out.println("9)Find User By Shared with pagination");
//		Page<User> userListByShared = userService.findByShared(true, 0, 3);
//		System.out.println("Total Elements : " + userListByShared.getTotalElements());
//		System.out.println("Total Pages : " + userListByShared.getTotalPages());
//		System.out.println("Number of Elements : " + userListByShared.getNumberOfElements());
//		userListByShared.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//		System.out.println("10)Find User By Suspended with pagination");
//		Page<User> userListBySuspended = userService.findBySuspended(true, 0, 3);
//		System.out.println("Total Elements : " + userListBySuspended.getTotalElements());
//		System.out.println("Total Pages : " + userListBySuspended.getTotalPages());
//		System.out.println("Number of Elements : " + userListBySuspended.getNumberOfElements());
//		userListBySuspended.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//		System.out.println("11)Find User By Role with pagination");
//		Page<User> userListByRole = userService.findByRole("admin", 0, 3);
//		System.out.println("Total Elements : " + userListByRole.getTotalElements());
//		System.out.println("Total Pages : " + userListByRole.getTotalPages());
//		System.out.println("Number of Elements : " + userListByRole.getNumberOfElements());
//		userListByRole.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//
//		System.out.println("12)Find User list By list of tags");
//		Page<User> usersslistByTagsList = userService.findTicketsByTagNames(Arrays.asList("Foxworth"), 0, 3);
//		System.out.println("Total Elements : " + usersslistByTagsList.getTotalElements());
//		System.out.println("Total Pages : " + usersslistByTagsList.getTotalPages());
//		System.out.println("Number of Elements : " + usersslistByTagsList.getNumberOfElements());
//		usersslistByTagsList.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//		System.out.println("13)Find All Tickets With Pagination Here with Page 2 and size 5");
//		Page<User> findAllUsersWithPagination = userService.findAllWithPagination( 2, 5);
//		System.out.println("Total Elements : " + findAllUsersWithPagination.getTotalElements());
//		System.out.println("Total Pages : " + findAllUsersWithPagination.getTotalPages());
//		System.out.println("Number of Elements : " + findAllUsersWithPagination.getNumberOfElements());
//		findAllUsersWithPagination.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//		System.out.println("14)List down All Users");
//		List<User> allUsersList = userService.findAll();
//		allUsersList.forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");
//
//		System.out.println("15)Find Users By Locale of : en-AU  With Pagination Here with Page 0 and size 3");
//		Page<User> findUsersByLocaleWithPagination = userService.findByLocale(new Locale.Builder().setLanguageTag("en-AU").build(), 0, 3);
//		System.out.println("Total Elements : " + findUsersByLocaleWithPagination.getTotalElements());
//		System.out.println("Total Pages : " + findUsersByLocaleWithPagination.getTotalPages());
//		System.out.println("Number of Elements : " + findUsersByLocaleWithPagination.getNumberOfElements());
//		findUsersByLocaleWithPagination.getContent().forEach(user -> System.out.println(user));
//		System.out.println("\n\n\n");



//		System.out.println("15)List down all user basic details");
//		List<UserDetails> userDetailsList = userService.getUserBasicDetails();
//		userDetailsList.forEach(userDetails -> System.out.println(userDetails));
