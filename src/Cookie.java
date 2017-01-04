import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Cookie {
	private static final long YEARS_SHIFT = 369;
	private String name;
	private String domain;
	private LocalDateTime time;

	public Cookie(String name, String domain, long  timestamp) {
		this.name = name;
		this.domain = domain;
		this.time = LocalDateTime.ofEpochSecond(timestamp/1000000, 0, ZoneOffset.UTC).minusYears(YEARS_SHIFT);
	}


	public String getName() {
		return name;
	}

	public String getDomain() {
		return domain;
	}
	
	public LocalDateTime getDate() {
		return time;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Domain: %s, name: %s, time: %s", domain, name, time);
	}

}
