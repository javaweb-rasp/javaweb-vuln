package org.javaweb.vuln.repository;

import org.javaweb.vuln.entity.SysComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sysCommentRepository")
public interface SysCommentRepository extends JpaRepository<SysComment, Long>, JpaSpecificationExecutor<SysComment> {

	List<SysComment> findSysCommentBySysArticleArticleId(Long articleId);

}