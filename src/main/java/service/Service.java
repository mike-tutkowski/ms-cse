package service;

import java.util.List;

public interface Service {
    List<String> getBoroughs(String startsWith) throws Exception;
}
