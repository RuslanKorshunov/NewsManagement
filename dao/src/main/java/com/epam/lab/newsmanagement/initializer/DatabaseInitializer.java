package com.epam.lab.newsmanagement.initializer;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static Logger logger;
    private static final String FILE_NAME;
    private static final String DROP_DATABASE;
    private static final String ENCODING;

    static {
        logger = LogManager.getLogger();
        FILE_NAME = "backup.sql";
        ENCODING = "UTF-8";
        DROP_DATABASE = "DROP DATABASE IF EXISTS \"NewsDB\"";

    }

    private DataSource dataSource;
    private ClassLoader classLoader;

    @Autowired
    public DatabaseInitializer(DataSource dataSource, ClassLoader classLoader) {
        this.dataSource = dataSource;
        this.classLoader = classLoader;
    }

    public void createDatabase() {
        try (Connection connection = dataSource.getConnection();
             Reader reader = getReader()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(reader);
        } catch (DataAccessException | SQLException | IOException e) {
            logger.error(e);
        }
    }

    public void dropDatabase() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_DATABASE);
        } catch (DataAccessException | SQLException e) {
            logger.error(e);
        }
    }

    private Reader getReader() {
        Reader reader = null;
        String filePath = classLoader.getResource(FILE_NAME).getFile();
        logger.error(filePath);
        File script = new File(filePath);
        try {
            if (script.exists()) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(script), ENCODING));
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            logger.error(e);
        }
        return reader;
    }
}
