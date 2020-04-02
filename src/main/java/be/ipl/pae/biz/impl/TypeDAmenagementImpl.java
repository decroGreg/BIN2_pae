package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.TypeDAmenagement;

public class TypeDAmenagementImpl implements TypeDAmenagement, TypeDAmenagementDto {
  private int id;
  private String description;

  /**
   * Cree un objet TypeDAmenagementImpl.
   * 
   * @param id id du type d'amenagement.
   * @param description description du type d'amenagement.
   */
  public TypeDAmenagementImpl(int id, String description) {
    super();
    this.id = id;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
