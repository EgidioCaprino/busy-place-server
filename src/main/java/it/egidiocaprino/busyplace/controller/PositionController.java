package it.egidiocaprino.busyplace.controller;

import it.egidiocaprino.busyplace.model.Position;
import it.egidiocaprino.busyplace.repository.PositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import javax.sql.DataSource;

@Controller
public class PositionController {

    @Autowired
    DataSource dataSource;

    @Autowired
    PositionRepository positionRepository;

    @RequestMapping(value = "/api/position", method = RequestMethod.GET)
    public @ResponseBody Integer getPositions(@RequestParam double latitude,
                                              @RequestParam double longitude,
                                              @RequestParam double distance)
    {
        return positionRepository.getLastHourPositionsCount(latitude, longitude, distance);
    }

    @RequestMapping(value = "/api/position", method = RequestMethod.POST)
    public @ResponseBody Position create(@RequestBody Position position) {
        position.setDate(new Date());
        return positionRepository.save(position);
    }

}
