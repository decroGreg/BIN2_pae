package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.TypeDAmenagement;

public class TypeDAmenagementImpl implements TypeDAmenagement, TypeDAmenagementDto {
  private String description;

  /**
   * Cree un objet TypeDAmenagementImpl
   * 
   * @param description description du type d'amenagement.
   */
  public TypeDAmenagementImpl(String description) {
    super();
    this.description = description;
  }

  public TypeDAmenagementImpl() {
    super();
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }
}
