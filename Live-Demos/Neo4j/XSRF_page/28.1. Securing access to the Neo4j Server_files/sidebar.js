/**
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/* Sidebar loader.
 * Dynamically inserts a sidebar into the page.
 */

jQuery( document ).ready(  function()
{
  loadOnlineSidebar( jQuery );
});

function loadOnlineSidebar( $ )
{
  var sidebar = $( "<div id='sidebar' style='top:180px;width:360px;border-left:none;border-top:none;' />" );
  var PAGE = /.*\/(.+?)\./;
  var HTML_URL_BASE = "http://docs.neo4j.org/chunked/";
  var PDF_URL_BASE = "http://info.neotechnology.com/download-pdf.html?document=";
  var LEFT_COLUMN_CSS = {"margin-right" : "295px"};
  var mainContent = getMainContent();
  if ( !mainContent )
  {
    return;
  }
  mainContent.css( LEFT_COLUMN_CSS );
  $( "#disqus_thread" ).css( LEFT_COLUMN_CSS );
  $( "div.navfooter" ).css( LEFT_COLUMN_CSS );
  addIeInfo();
  addLinks();
  $( "html" ).append( sidebar );

  function addIeInfo()
  {
    if ( ! $.browser.msie ) return;
    var html = "<div id='msie-warning'><p><img src='images/icons/admon/important.png' style='height: 24px; width: 24px; float: left;' />Some cool features of this manual are not supported by <span style='font-weight: bold;'>Internet Explorer</span>. "
    + "Try a different browser like Chrome, Firefox or Safari to enjoy them!</p></div>"
      sidebar.append( $( html ) );
  }


  function addLinks()
  {
    var discussion = "disqus_thread";
    if ( $("#neo-disqus-wrapper").length > 0 )
    {
      discussion = "neo-disqus-wrapper";
    }
    var html = "<div style='border-bottom: none; padding: 5px 5px 0 5px; float: left; margin-right: 10px; margin-left: 100px;'><a href='#" + discussion + "'><img title='Ask a question.' src='http://www.neotechnology.com/wp-content/uploads/2012/08/comments.png' alt='' width='70' height='70' /> </a></div>";
    var url = PDF_URL_BASE + neo4jVersion;
    html += "<div style='border-bottom: none; padding: 5px 5px 0 5px; float: left; margin-right: 10px;'><a href='" + url + "'><img title='Get a PDF version of this Neo4j Manual.' src='http://www.neotechnology.com/wp-content/uploads/2012/08/pdf.png' alt='' width='70' height='70' /></a></div>";
    html += "<iframe src='http://www.neotechnology.com/sidebar/' id='neotech-sidebar' style='width:360px;height:2100px;' frameborder='0' scrolling='no' />";
    sidebar.append( $( html ) );
  }

  function getMainContent()
  {
    var mainContent;
    var navHeader = $( "body > div.navheader" );
    if ( navHeader && navHeader[0] )
    {
      mainContent = $( navHeader[0] ).next();
    }
    return mainContent;
  }

}


