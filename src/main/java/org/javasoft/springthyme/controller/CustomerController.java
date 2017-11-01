/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.controller;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.javasoft.springthyme.constants.CustomerPageResource;
import org.javasoft.springthyme.constants.TableResource;
import org.javasoft.springthyme.entity.Address;
import org.javasoft.springthyme.entity.Customer;
import org.javasoft.springthyme.service.intf.CustomerServiceIntf;
import org.javasoft.springthyme.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ayojava
 */
@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController implements CustomerPageResource, TableResource {

    private String[] titles = {"Mr", "Mrs", "Dr", "Prof", "Deacon"};

    private String[] gender = {"Male", "Female"};

    private final CustomerServiceIntf customerServiceIntf;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("customerLink", "active");
        
    }

    @Autowired
    public CustomerController(CustomerServiceIntf customerServiceIntf) {
        this.customerServiceIntf = customerServiceIntf;
    }

    

//    @ModelAttribute("customerTitle")
//    public String[] getTitles() {
//        return titles;
//    }
//
//    @ModelAttribute("genders")
//    public String[] getGender() {
//        return gender;
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String customerHomePage(HttpServletRequest request) {
        request.getSession().setAttribute("customerList", null);
        return "redirect:/customer/list/1";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/{pageNumber}")
    public String listCustomers(HttpServletRequest request, @PathVariable(required = true) Integer pageNumber, Model model) {

        PagedListHolder<Customer> customerPagedListHolder = (PagedListHolder<Customer>) request.getSession().getAttribute("customerList");
        if (customerPagedListHolder == null) {
            customerPagedListHolder = new PagedListHolder();
            customerPagedListHolder.setSource(customerServiceIntf.findAllCustomers());
            customerPagedListHolder.setPageSize(PAGE_LIST_SIZE);
        } else {
            final int destPage = pageNumber - 1;
            if (destPage <= customerPagedListHolder.getPageCount() && destPage >= 0) {
                customerPagedListHolder.setPage(destPage);
            }
        }

        request.getSession().setAttribute("customerList", customerPagedListHolder);

        int currentIndex = customerPagedListHolder.getPage() - 1;
        int beginIndex = Math.max(1, currentIndex - PAGE_LIST_SIZE);
        int endIndex = Math.min(beginIndex + 5, customerPagedListHolder.getPageCount());
        int totalPageCount = customerPagedListHolder.getPageCount();
        String customerListURL = "/customer/list/";

        model.addAttribute(BEGIN_INDEX, beginIndex);
        model.addAttribute(END_INDEX, endIndex);
        model.addAttribute(CURRENT_INDEX, currentIndex);
        model.addAttribute(TOTAL_PAGE_COUNT, totalPageCount);
        model.addAttribute(BASE_URL, customerListURL);
        model.addAttribute(DISPALY_MODE, LIST_DISPLAY_MODE);
        model.addAttribute(CUSTOMER_PAGED_LIST, customerPagedListHolder);
        //

        return LIST_CUSTOMER_PAGE;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String registerNewCustomer(Model model) {
        
        Customer customerObj = new Customer();
        Address addressTemplate = new Address();
        customerObj.setAddressTemplate(addressTemplate);
        model.addAttribute("customerTitle", titles);
        model.addAttribute("genders", gender);
        model.addAttribute("customer", customerObj);
        return NEW_CUSTOMER_PAGE;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/view/{customerID}")
    public String viewCustomer(HttpServletRequest request,Model model,RedirectAttributes ra,@PathVariable(required = true) Long customerID){
        log.info("========= Called viewCustomer Method ============");
        PagedListHolder<Customer> customerPagedListHolder = (PagedListHolder<Customer>) request.getSession().getAttribute("customerList");
        List<Customer> customerList = customerPagedListHolder.getSource();
        Optional<Customer> optionalCustomer= customerList.stream().filter(cust->{
            return customerID.compareTo(cust.getCustomerID())== 0 ;
        }).findFirst();
        if(optionalCustomer.isPresent()){
            model.addAttribute("customer",optionalCustomer.get());
            return VIEW_CUSTOMER_PAGE;
        }else{
            MessageUtil.addRedirectErrorAttribute(ra, "recordNotFound.error");
        }
        return "redirect:/customer";
    }
        
    @RequestMapping(method = RequestMethod.GET, value = "/edit/{customerID}")
    public String editCustomer(Model model,@PathVariable(required = true) Long customerID){
        model.addAttribute("customer",customerServiceIntf.findCustomerByID(customerID));
        model.addAttribute("customerTitle", titles);
        model.addAttribute("genders", gender);
        return EDIT_CUSTOMER_PAGE;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    public String editCustomer(@Valid @ModelAttribute Customer customer, BindingResult results, Model model,RedirectAttributes ra){
        if(results.hasErrors()){
            MessageUtil.addWarningAttribute(model, "validation.error");
            return EDIT_CUSTOMER_PAGE;
        }
        customerServiceIntf.editCustomer(customer);
        MessageUtil.addRedirectSuccessAttribute(ra, "edit.success");
        return "redirect:/customer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/new")
    public String saveNewCustomer(@Valid @ModelAttribute Customer customer, BindingResult results, Model model,RedirectAttributes ra) {
        if(results.hasErrors()){
            MessageUtil.addWarningAttribute(model, "validation.error");
            return NEW_CUSTOMER_PAGE;
        }
        customer.setRegNo(RandomStringUtils.randomAlphanumeric(5));
        customerServiceIntf.saveCustomer(customer);
        MessageUtil.addRedirectSuccessAttribute(ra, "save.success");
        return "redirect:/customer";
    }
}



/*
//@RequestParam Long id
//https://github.com/dandelion/dandelion-datatables-samples/blob/master/datatables-thymeleaf-starter/src/main/webapp/WEB-INF/views/basics/paging/bootstrap-simple.html
//https://github.com/dandelion/dandelion-datatables-samples/blob/master/datatables-thymeleaf-starter/src/main/java/com/github/dandelion/datatables/web/SampleController.java
//http://dandelion.github.io/components/datatables/1.1.0/docs/html/


 * https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/
 * https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/
 * http://javasampleapproach.com/spring-framework/create-paging-sorting-result-spring-jpa-postgresql-spring-boot
 * https://www.javatpoint.com/spring-mvc-pagination-example
 * http://nixmash.com/post/pagination-with-spring-mvc-solr-thymeleaf-and-bootstrap
 * http://www.kswaughs.com/2016/04/spring-pagination-example.html
 * http://kartikstechblog.blogspot.com.ng/2015/07/spring-mvc-nuggets-pagedlistholder.html
 * http://www.developer.com/java/web/article.php/10935_3830886_2/A-Pagination-Technique-Using-Spring.htm
 * http://www.ekiras.com/2015/03/pagination-in-spring-hibernate-mvc-application-using-criteria-query.html
 * http://jtuts.com/2015/01/29/create-custom-spring-form-errors-tag/
 * https://github.com/mintster/nixmash-spring
 * 
 * */
 
