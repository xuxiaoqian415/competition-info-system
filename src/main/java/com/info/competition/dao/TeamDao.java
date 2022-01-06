package com.info.competition.dao;

import com.info.competition.model.Query;
import com.info.competition.model.Team;
import com.info.competition.model.dto.TeamDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamDao {
    /**
     * 增加团队
     */
    Integer insertTeam(Team team);

    /**
     * 根据竞赛id与队长id查询团队id
     */
    Integer selectTeamId(@Param("cpId") Integer cpId, @Param("leaderId") Integer leaderId);

    /**
     * 获取所有团队信息
     */
    List<TeamDto> selectAll(Query query);

    /**
     * 根据id删除团队
     */
    Integer deleteTeam(@Param("id") Integer id);

    /**
     * 团队更新
     */
    Integer updateTeam(Team team);

    /**
     * 根据团队id查询团队
     */
    Team selectTeamById(@Param("id") Integer id);
}
