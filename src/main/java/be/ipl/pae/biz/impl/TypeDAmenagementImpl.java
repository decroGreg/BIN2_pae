package be.ipl.pae.biz.impl;

import be.ipl.pae.biz.dto.TypeDAmenagementDTO;
import be.ipl.pae.biz.interfaces.TypeDAmenagement;

public class TypeDAmenagementImpl implements TypeDAmenagement, TypeDAmenagementDTO {
  private String description;

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
