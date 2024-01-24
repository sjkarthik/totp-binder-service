package io.mosip.totpbinderservice.config;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {
    public InfoProperty getInfo() {
		return info;
	}
	public void setInfo(InfoProperty info) {
		this.info = info;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	private InfoProperty info;
    private Service service;
}

class InfoProperty {
    private String title;
    private String description;
    private String version;
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public LicenseProperty getLicense() {
		return license;
	}
	public void setLicense(LicenseProperty license) {
		this.license = license;
	}
	private LicenseProperty license;
}

class LicenseProperty {
    private String name;
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private String url;
}

class Service {
    private List<Server> servers;

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}
}

class Server {
    private String description;
    private String url;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}