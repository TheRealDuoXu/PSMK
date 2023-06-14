package model.database.dao;

import model.session.UninstallExclusive;

@UninstallExclusive
public class UninstallDAO extends DAO{
    @UninstallExclusive
    public void dropDatabase(){

    }
}
