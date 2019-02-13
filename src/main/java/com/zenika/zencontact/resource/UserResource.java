package com.zenika.zencontact.resource;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.fetch.PartnerBidthdateService;
import com.zenika.zencontact.persistence.cache.MemCacheService;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserResource", value = "/api/v0/users")
public class UserResource extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json; charset=utf-8");

    MemcacheService cache = MemCacheService.getInstance().memCache;

    List<User> contacts = (List<User>) cache.get(MemCacheService.CONTACTS_CACHES_KEY);

    if(contacts == null) {
      contacts = UserDaoObjectify.getInstance().getAll();
      cache.put(
              MemCacheService.CONTACTS_CACHES_KEY,
              contacts,
              Expiration.byDeltaSeconds(240),
              MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT
      );
    }

    response.getWriter().println(new Gson().toJsonTree(contacts).getAsJsonArray());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    User user = new Gson().fromJson(request.getReader(), User.class);
    String birthdate = PartnerBidthdateService.getInstance().findBirthdate(
            user.firstName,
            user.lastName
    );
    if (birthdate != null) {
      try {
        user.birthdate(new SimpleDateFormat("yyyy-MM-dd").parse(birthdate));
      } catch (ParseException e) {}
    }
    user.id(UserDaoObjectify.getInstance().save(user));

    // Delete cache
    MemcacheService cache = MemCacheService.getInstance().memCache;
    cache.delete(MemCacheService.CONTACTS_CACHES_KEY);

    response.setContentType("application/json; charset=utf-8");
    response.setStatus(201);
    response.getWriter().println(new Gson().toJson(user));
  }
}
