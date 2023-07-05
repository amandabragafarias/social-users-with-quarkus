package com.social.quarkus.repository;

import com.social.quarkus.entities.Follower;
import com.social.quarkus.entities.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
    public boolean followers (User follower, User user){
        Map<String, Object> params =
                Parameters.with("follower", follower)
                          .and("user", user)
                          .map();
        PanacheQuery<Follower> query = find("follower = :follower and user = :user", params);
        Optional<Follower> result = query.firstResultOptional();

        return result.isPresent();
    }

    public List<Follower> findByUser(Long user_id){
        PanacheQuery<Follower> query = find("user.id", user_id);
        return query.list();

    }

    public void unfollowUser(Long user_id, Long follower_id){
        Map<String, Object> params =
                Parameters.with("user_id", user_id)
                        .and("follower_id", follower_id)
                        .map();
        delete("user.id = :user_id and follower.id = :follower_id", params);


    }
}
