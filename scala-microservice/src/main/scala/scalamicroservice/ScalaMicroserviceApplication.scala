package scalamicroservice;

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
class ScalaMicroserviceApplication extends SpringBootServletInitializer {

}

object ScalaMicroserviceApplication extends App {
	SpringApplication.run(classOf[ScalaMicroserviceApplication], args: _*)
}
