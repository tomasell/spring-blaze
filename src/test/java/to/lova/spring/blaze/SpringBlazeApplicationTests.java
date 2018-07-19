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
import to.lova.spring.blaze.repository.PostDetailViewRepository;
import to.lova.spring.blaze.repository.PostListViewRepository;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BlazeConfiguration.class)
public class SpringBlazeApplicationTests {

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    public void populateRepository() {
        var p1 = new User();
        p1.setUserName("Giovanni");
        this.em.persist(p1);

        var p2 = new User();
        p2.setUserName("Alex");
        this.em.persist(p2);

        var a1 = new Post();
        a1.setPoster(p1);
        var c1 = new Comment();
        c1.setComment("First comment.");
        c1.setCommenter(p1);
        var c2 = new Comment();
        c2.setComment("It works!");
        c2.setCommenter(p2);
        a1.setComments(List.of(c1, c2));
        this.em.persist(a1);

        this.em.flush();
        this.em.clear();
    }

    @Test
    public void testFindAllViews(@Autowired PostListViewRepository repository) {
        var articles = repository.findAll();
        assertEquals(1, articles.size());
        assertEquals("Giovanni", articles.get(0).getPoster().getUserName());
    }

    @Test
    public void testFindDetailViews(
            @Autowired PostDetailViewRepository repository) {
        var articles = repository.findAll();
        assertEquals(1, articles.size());
        assertEquals("Giovanni", articles.get(0).getPoster().getUserName());
        assertEquals(2, articles.get(0).getComments().size());
    }

}
