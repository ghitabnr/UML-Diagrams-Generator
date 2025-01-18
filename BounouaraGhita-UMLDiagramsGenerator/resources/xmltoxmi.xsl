<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xmi="http://www.omg.org/spec/XMI"
                xmlns:uml="http://www.omg.org/spec/UML"
                version="2.0">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/projet">
        <xmi:XMI xmlns:xmi="http://www.omg.org/spec/XMI" xmlns:uml="http://www.omg.org/spec/UML">
            <xmi:Documentation exporter="XSLT Transformation" exporterVersion="1.0"/>
            <uml:Model xmi:id="model" name="{/@name}">
                <xsl:apply-templates select="package"/>
            </uml:Model>
        </xmi:XMI>
    </xsl:template>

    <xsl:template match="package">
        <uml:Package xmi:id="{generate-id()}" name="{/@name}">
            <xsl:apply-templates select="classe | interface"/>
        </uml:Package>
    </xsl:template>

    <xsl:template match="classe">
        <uml:Class xmi:id="{generate-id()}" name="{/@name}">
            <xsl:apply-templates select="fields/field"/>
            <xsl:apply-templates select="methods/method"/>
            <xsl:apply-templates select="relations/relation"/>
        </uml:Class>
    </xsl:template>

    <xsl:template match="field">
        <uml:Property xmi:id="{generate-id()}" name="{/@name}" visibility="{normalize-space(@modifier)}" type="{/@type}"/>
    </xsl:template>

    <xsl:template match="method">
        <uml:Operation xmi:id="{generate-id()}" name="{/@name}" visibility="{normalize-space(@modifier)}">
            <uml:Parameter xmi:id="{generate-id()}-return" name="return" type="{/@type}"/>
        </uml:Operation>
    </xsl:template>

    <xsl:template match="relation">
        <xsl:choose>
            <xsl:when test="text() = 'extension'">
                <uml:Generalization xmi:id="{generate-id()}" general="{/@classe}" specific="{ancestor::classe/@name}"/>
            </xsl:when>
            <xsl:when test="text() = 'implementation'">
                <uml:InterfaceRealization xmi:id="{generate-id()}" client="{ancestor::classe/@name}" supplier="{/@classe}"/>
            </xsl:when>
            <xsl:when test="text() = 'Aggregation'">
                <uml:Association xmi:id="{generate-id()}">
                    <uml:ownedEnd xmi:id="{generate-id()}-end1" type="{ancestor::classe/@name}"/>
                    <uml:ownedEnd xmi:id="{generate-id()}-end2" type="{/@classe}"/>
                </uml:Association>
            </xsl:when>
            <xsl:when test="text() = 'Composition'">
                <uml:Association xmi:id="{generate-id()}" aggregation="composite">
                    <uml:ownedEnd xmi:id="{generate-id()}-end1" type="{ancestor::classe/@name}"/>
                    <uml:ownedEnd xmi:id="{generate-id()}-end2" type="{/@classe}"/>
                </uml:Association>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="interface">
        <uml:Interface xmi:id="{generate-id()}" name="{/@name}">
            <xsl:apply-templates select="methods/method"/>
            <xsl:apply-templates select="relations/relation"/>
        </uml:Interface>
    </xsl:template>
</xsl:stylesheet>
