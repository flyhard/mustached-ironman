package com.abich.core;


import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

public class MemberBuilder {
    private String id = null;
    private String name = null;
    private String emailAddress = null;
    private List<EmailAddressBuilder> alternativeAddresses = Lists.newArrayList();
    private String phone;

    public MemberBuilder clone(Member member) {
        id = member.getId();
        name = member.getName();
        emailAddress = member.getEmailAddress();
        phone = member.getPhone();
        alternativeAddresses = Lists.transform(member.getAlternativeAddresses(), new Function<EmailAddress, EmailAddressBuilder>() {
            @Nullable
            @Override
            public EmailAddressBuilder apply(@Nullable final EmailAddress input) {
                return new EmailAddressBuilder().clone(input);
            }
        });
        return this;
    }

    public MemberBuilder setId(final String id) {
        this.id = id;
        return this;
    }

    public MemberBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public MemberBuilder setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public MemberBuilder cloneAlternativeAddresses(List<EmailAddress> alternativeAddresses) {
        this.alternativeAddresses = Lists.newArrayList();
        for (EmailAddress alternativeAddress : alternativeAddresses) {
            EmailAddressBuilder emailAddressBuilder = new EmailAddressBuilder().clone(alternativeAddress);
            this.alternativeAddresses.add(emailAddressBuilder);
        }
        return this;
    }

    public MemberBuilder setAlternativeAddresses(EmailAddressBuilder... alternativeAddresses) {
        this.alternativeAddresses = Lists.newArrayList(alternativeAddresses);

        return this;
    }

    public Member createMember() {
        List<EmailAddress> emailAddresses = Lists.transform(Lists.newArrayList(alternativeAddresses), new Function<EmailAddressBuilder, EmailAddress>() {
            @Nullable
            @Override
            public EmailAddress apply(final EmailAddressBuilder addressBuilder) {
                return addressBuilder.createEmailAddress();
            }
        });
        return new Member(id, name, emailAddress, emailAddresses, phone);
    }

    public MemberBuilder setPhone(final String phone) {
        this.phone = phone;
        return this;
    }
}