package com.cabapplication.user;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class CabUsersApplication {
//test
	public static void main(String[] args) {
		SpringApplication.run(CabUsersApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return new CommandLineRunner() {
			
//			@Autowired
//			private EmployeeRepository repo;
//			
//			@Autowired
//			DestinationRepository destinationRepo;
//			
//			@Autowired
//			SourceRepository sourceRepo;
////			
//			@Autowired
//			private TripCabInfoRepository cabRepo;
//			
//			@Autowired
//			private BookingRepository bookingRepository;
//			
//			@Autowired
//			private DriverRepository driverRepo;
//			
			@Override
			public void run(String... args) throws Exception {
				
//				List<BookingRequest> reqs = bookingRepository.findByTripCabId(10);
//				for(BookingRequest req : reqs) {
//					req.setReachedTime(LocalTime.now().plusHours(2));
//					req.setStartTime(LocalTime.now());
//					req.setStatus("Reached");
//					bookingRepository.save(req);
//				}
//				
//				TripCabInfo cabInfo = cabRepo.findById(10L).get();
//				cabInfo.setStartTime(LocalTime.now());
//				cabInfo.setEndTime(LocalTime.now());
//				cabInfo.setStatus("Reached");
//				
//				cabRepo.save(cabInfo);
				
//				sourceRepo.save(new Source("AlphaCity", "Test", LocalDateTime.now(), null, null, 0));
//				sourceRepo.save(new Source("BayLine", "Test", LocalDateTime.now(), null, null, 0));
				
//				cabRepo.save(new TripCabInfo(10, "TNXXXXXX", 1, "Alpha City", "Velachery", "Velachery", LocalDate.now(), LocalTime.now(), 10, 3, 7, "Not Reached", LocalTime.of(8, 0), LocalTime.of(9, 0)));
//				
//				driverRepo.save(new Driver(1, "Ravi", 12345678, "Ravi", 1234, LocalDate.of(2025, 1, 1)));
				
//				this.repo.save(new Employee("AVI_105", "Prakash", "prakash@avasoft.com", 
//						1234567890, true, false, "Java", "Rohit", "avasoft-cabs",
//						"arvinth", "Jithendra", LocalDateTime.now(), null, null, false));
//				
//				this.repo.save(new Employee("AVI_101", "Jithendra", "jithendra@avasoft.com", 
//						1234567890, false, false, "Java", "Rohit", "avasoft-cabs",
//						"arvinth", "Jithendra", LocalDateTime.now(), null, null, false));
//				
//				this.repo.save(new Employee("AVI_102", "Jith", "jith@avasoft.com",
//						1234567890, false, true, "Java", "Rohit", "avasoft-cabs",
//						"arvinth", "Jithendra", LocalDateTime.now(), null, null, false));
//				
//				this.repo.save(new Employee("AVI_103", "Kishore", "kishore@avasoft.com",
//						1234567890, true, false, "Java", "Rohit", "avasoft-cabs",
//						"arvinth", "Jithendra", LocalDateTime.now(), null, null, false));
//				
//				this.repo.save(new Employee("AVI_104", "Test", "thane@avam365test.onmicrosoft.com", 
//						1234, true, false, null, null, null, null, null, null, null, null, false));
//				
//				DropPoint drop1 = new DropPoint(1, "Sholingnallur");
//				DropPoint drop2 = new DropPoint(2, "water Tank");
//				DropPoint drop3 = new DropPoint(3, "VGP");
//				DropPoint drop4 = new DropPoint(4, "Neelangari");
//				DropPoint drop5 = new DropPoint(5, "Paalavakkam");
//				DropPoint drop6 = new DropPoint(6, "Thiruvanmiyur");
//				DropPoint drop7 = new DropPoint(7, "Kelambakkam");
//				List<DropPoint> dropPoints = new ArrayList<>();
//				dropPoints.add(drop1);
//				dropPoints.add(drop2);
//				dropPoints.add(drop3);
//				dropPoints.add(drop4);
//				dropPoints.add(drop5);
//				dropPoints.add(drop6);
//				dropPoints.add(drop7);
//				
//				TimeSlot slot1 = new TimeSlot(1, LocalTime.of(19, 30));
//				TimeSlot slot2 = new TimeSlot(2, LocalTime.of(20, 30));
//				TimeSlot slot3 = new TimeSlot(3, LocalTime.of(21, 30));
//				TimeSlot slot4 = new TimeSlot(4, LocalTime.of(22, 30));
//				TimeSlot slot5 = new TimeSlot(5, LocalTime.of(23, 30));
//				TimeSlot slot6 = new TimeSlot(6, LocalTime.of(00, 30));
//				TimeSlot slot7 = new TimeSlot(7, LocalTime.of(2, 00));
//				List<TimeSlot> timeSlots = new ArrayList<>();
//				timeSlots.add(slot1);
//				timeSlots.add(slot2);
//				timeSlots.add(slot3);
//				timeSlots.add(slot4);
//				timeSlots.add(slot5);
//				timeSlots.add(slot6);
//				timeSlots.add(slot7);
//				
//				Destination destination = new Destination(4, "Thiruvanmiyur",dropPoints,timeSlots);
//				this.destinationRepo.save(destination);
//				
//				
//				Source source1 = new Source(1, "BayLine");
//				Source source2 = new Source(2, "AlphaCity");
//				this.sourceRepo.saveAll(Arrays.asList(source1,source2));
			}
		};
	}
	
}
