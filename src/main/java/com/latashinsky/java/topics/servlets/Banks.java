package com.latashinsky.java.topics.servlets;

import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

@WebServlet(name = "Banks", urlPatterns = "/banks/*")
public class Banks extends HttpServlet {
    MyRepository<Bank> myBankRepository;

    @Override
    public void init() {
        myBankRepository = Factory.getInstance().getRepository(Bank.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String[] path = url.split("/");
        switch (path.length) {
            case 2: {
                getAllBanksWithFilter(request, response);
                break;
            }
            case 3: {
                getBankById(response, path[2]);
                break;
            }
            default: {
                response.setStatus(404);
                break;
            }
        }
    }

    private void getBankById(HttpServletResponse response, String id) throws IOException {
        Bank bank = myBankRepository.findById(
                UUID.fromString(id));
        if (bank == null) {
            response.setStatus(404);
            return;
        }
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(bank));
    }

    private void getAllBanksWithFilter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Collection<Bank> banks = myBankRepository.findAll();
        String name = request.getParameter("name");
        if (name != null) {
            banks.removeIf(r -> !r.getName().equalsIgnoreCase(name));
        }
        response.getWriter().write(new ObjectMapper().writeValueAsString(banks));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String legalCommission = request.getParameter("legalCommission");
        String usualCommission = request.getParameter("usualCommission");
        if (request.getRequestURI().split("/").length == 2 && name != null && legalCommission != null && usualCommission != null) {
            Bank bank = Factory.getInstance().getEntity(Bank.class);
            bank.setName(name);
            if (Pattern.matches(Constants.PATTERN_DOUBLE, legalCommission)
                    && Pattern.matches(Constants.PATTERN_DOUBLE, usualCommission)) {
                bank.setUsualCommission(new BigDecimal(usualCommission));
                bank.setLegalCommission(new BigDecimal(legalCommission));
            } else {
                response.setStatus(404);
                return;
            }
            myBankRepository.save(bank);
            response.sendRedirect("/banks");
        }
        response.setStatus(404);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] path = request.getRequestURI().split("/");
        if (path.length == 3) {
            Bank bank = myBankRepository.findById(UUID.fromString(path[2]));
            if (bank == null) {
                response.setStatus(404);
                return;
            }
            String name = request.getParameter("name");
            String legalCommission = request.getParameter("legalCommission");
            String usualCommission = request.getParameter("usualCommission");
            if (name != null) {
                bank.setName(name);
            }
            if (legalCommission != null && Pattern.matches(Constants.PATTERN_DOUBLE, legalCommission)) {
                bank.setLegalCommission(new BigDecimal(legalCommission));
            }
            if (usualCommission != null && Pattern.matches(Constants.PATTERN_DOUBLE, usualCommission)) {
                bank.setUsualCommission(new BigDecimal(usualCommission));
            }
            myBankRepository.save(bank);
            response.sendRedirect("/banks");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] path = request.getRequestURI().split("/");
        if (path.length == 3) {
            myBankRepository.delete(myBankRepository.findById(UUID.fromString(path[2])));
            response.sendRedirect("/banks");
        }
    }
}
