package com.amigotrip.repository;

import com.amigotrip.domain.Theme;
import com.amigotrip.domain.enums.ThemeName;
import org.springframework.data.repository.CrudRepository;

public interface ThemeRepository extends CrudRepository<Theme, Long> {
    Theme findByThemeName(String themeName);
}
