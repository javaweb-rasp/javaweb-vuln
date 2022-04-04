package org.javaweb.vuln.repository;

import org.javaweb.vuln.entity.SysArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sysArticleRepository")
public interface SysArticleRepository extends JpaRepository<SysArticle, Long>, JpaSpecificationExecutor<SysArticle> {

	List<SysArticle> findSysArticleByAuthor(String author);

	Page<SysArticle> findAllByOrderByPublishDateDesc(Pageable pageable);

	@Modifying
	@Query("update SysArticle set clickCount = clickCount+1 where articleId = ?1")
	void updateSysArticleClickCount(Long articleId);

	@Query(value = "select * from (select *," +
			"(select count(1) from sys_comment as c where c.article_id = a.article_id ) as commentCount" +
			" from sys_article as a) as article " +
			"where article.commentCount > 0 order by article.commentCount desc limit ?1", nativeQuery = true)
	List<SysArticle> getSysArticleTops(int limit);

}