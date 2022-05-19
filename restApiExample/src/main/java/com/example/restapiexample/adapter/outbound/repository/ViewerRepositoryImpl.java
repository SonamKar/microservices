package com.example.restapiexample.adapter.outbound.repository;

import com.example.restapiexample.core.dbentity.Viewer;
import com.example.restapiexample.core.port.outbound.ViewerRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViewerRepositoryImpl implements ViewerRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public List<Viewer> getViewerDetails(String name) {
    final MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("viewerName", name);

    return namedParameterJdbcTemplate.query(
        "select * from viewers where name=:viewerName",
        params,
        (rs, rowNum) ->
            Viewer.builder()
                .name(rs.getString("name"))
                .age(rs.getInt("age"))
                .preferredMovieType(rs.getString("preferred_movie_type"))
                .build());

//Anonymous Class impl
//        new RowMapper<Viewer>() {
//          @Override
//          public Viewer mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return Viewer.builder()
//                .name(rs.getString("name"))
//                .age(rs.getInt("age"))
//                .preferredMovieType(rs.getString("preferred_movie_type"))
//                .build();
//          }
//        });
  }
}

