package model.database.dao;

import model.session.UninstallExclusive;

import java.lang.annotation.Documented;

@UninstallExclusive
public class UninstallDAO extends DAO{
    @UninstallExclusive
    public void dropDatabase(){

    }
}
