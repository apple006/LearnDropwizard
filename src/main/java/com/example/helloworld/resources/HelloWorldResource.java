package com.example.helloworld.resources;

import com.example.helloworld.core.Saying;
import com.example.helloworld.vo.Customer;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Object sayHello(@QueryParam("name") Optional<String> name) throws IOException {
        final String value = String.format(template, name.or(defaultName));
        return value;
    }

    @GET
    @Path("/properties")
    @Timed
    public Object getProperties() throws IOException {
        List<Customer> customerList = new ArrayList<Customer>();
        for(int i = 0; i < 100; i++){
            Customer customer = new Customer();
            customer.setCustomerno("客户信息" + i);
//            customer.setCustomerno("dongp" + i);

            customer.setPlatform(i%10 + "");
            customerList.add(customer);
        }
        return customerList;
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Saying getSomeThine(final Saying say) {
        System.out.println(say.getContent());
        System.out.println(say.getId());
        say.setContent("came from dropwizard_learn");
        return say;
    }
}