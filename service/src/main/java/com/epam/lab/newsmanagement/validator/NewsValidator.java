package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsValidator implements Validator<News> {
    @Autowired
    private static Logger logger;

    @Override
    public void validate(News news) throws IncorrectDataException {
        if (news == null) {
            throw new IncorrectDataException("News can't be null.");
        }
        String title = news.getTitle();
        if (title == null) {
            throw new IncorrectDataException("Title can't be null.");
        }
        if (title.length() > 30) {
            title = title.substring(0, 29);
            news.setTitle(title);
            logger.warn("News's title was cut because it length is more than 30 symbols.");
        }
        String shortText = news.getShortText();
        if (shortText == null) {
            throw new IncorrectDataException("ShortText can't be null.");
        }
        if (shortText.length() > 100) {
            shortText = shortText.substring(0, 99);
            news.setShortText(shortText);
            logger.warn("News's shortText was cut because it length is more than 100 symbols.");
        }
        String fullText = news.getFullText();
        if (fullText == null) {
            throw new IncorrectDataException("FullText can't be null.");
        }
        if (fullText.length() > 2000) {
            fullText = fullText.substring(0, 1999);
            news.setFullText(fullText);
            logger.warn("News's fullText was cut because it length is more than 2000 symbols.");
        }
    }
}
