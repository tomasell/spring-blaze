/*-
 * Copyright 2017-2018 Axians SAIV S.p.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
-*/
package to.lova.spring.blaze;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import to.lova.spring.blaze.entity.Comment;
import to.lova.spring.blaze.entity.Post;
import to.lova.spring.blaze.entity.User;
import to.lova.spring.blaze.repository.PostWithCommentCountViewRepository;
import to.lova.spring.blaze.repository.PostWithPosterCommentCountViewRepository;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BlazeConfiguration.class)
public class SpringBlazeApplicationTests {

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    public void populateRepository() {
        var u1 = new User();
        u1.setUserName("Giovanni");
        this.em.persist(u1);

        var u2 = new User();
        u2.setUserName("Alex");
        this.em.persist(u2);

        var p1 = new Post();
        p1.setPoster(u1);
        var c1 = new Comment();
        c1.setComment("First comment.");
        c1.setCommenter(u1);
        // this.em.persist(c1);

        var c2 = new Comment();
        c2.setComment("It works!");
        c2.setCommenter(u2);
        // this.em.persist(c2);

        p1.setComments(List.of(c1, c2));
        this.em.persist(p1);

        this.em.flush();
        this.em.clear();
    }

    @Test
    public void testPostWithCommentCountView(
            @Autowired PostWithCommentCountViewRepository repository) {
        var articles = repository.findAll();
        assertEquals(2L, articles.get(0).getCommentCount().longValue());
    }

    @Test
    public void testPostWithPosterCommentCountView(
            @Autowired PostWithPosterCommentCountViewRepository repository) {
        var articles = repository.findAll();
        assertEquals(1L, articles.size());
        assertEquals(2L, articles.get(0).getCommentCount().longValue());
        assertEquals(1L, articles.get(0).getPosterCommentCount().longValue());
    }

}
