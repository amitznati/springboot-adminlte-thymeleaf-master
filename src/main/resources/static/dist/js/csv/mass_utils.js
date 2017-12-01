function mergeArrays(a,b){
    var ret = [];
    for(var k in a){
        if(a.hasOwnProperty(k)) 
            ret[k] = a[k];
    }
    for(var k in b){
        if(b.hasOwnProperty(k)) 
            ret[k] = b[k];
    }
    return ret;
}

function parsAttributesList(attrList){
    if(!attrList) return [];
    var attributesList = [];
    var componentArr = [attrList];
    if(attrList.includes(';'))
        componentArr = attrList.split(';');
    componentArr.forEach(function(comp){
        var component  = new Component();
        var compAttrList = comp.split('||');
        component.cid(compAttrList[0]);
        for(j=1;j<compAttrList.length;j++){
            var attrAndVal = compAttrList[j].split('=');
            var attribute = new Attribute();
            attribute.name(attrAndVal[0]);
            attribute.value(attrAndVal[1]);
            component.attributes.push(attribute);
        }
        attributesList.push(component);
    })
    return attributesList;
}

function parsToStringAttributesList(attributesList){
    var attrListString = "";
    if(!attributesList)
        return attrListString;
    attributesList.forEach(function(comp){
        attrListString += comp.cid() + "||";
        comp.attributes().forEach(function(attr){
            attrListString += attr.name() + "=" + attr.value() + "||";
        });
        attrListString = attrListString.substr(0,attrListString.length-2);
        attrListString += ";";
    })
    return attrListString;
}

function getMassRequestFromText(text){
    var massRequest = new MassRequest();
    var textAsArray = text.split('\n');
    console.log(textAsArray);
    massRequest.headerAttributesString(textAsArray[0]);
    massRequest.detailsAttributesString(textAsArray[2]);
    massRequest.linesAttributesString(textAsArray[4]);
    var headerAttrs = textAsArray[0].split(',');
    var headerValues = textAsArray[1].split(',');
    var detailsAttrs = textAsArray[2].split(',');
    var detailsValues = textAsArray[3].split(',');
    var linesAttrs = textAsArray[4].split(',');
    var lines = textAsArray.slice(5);
    for(i =0 ; i<headerAttrs.length;i++){
        if(headerAttrs[i]){
            var attribute = new Attribute(headerAttrs[i],headerValues[i]);
            massRequest.header.push(attribute);
        }
        
    };
    console.log('header parsed');

    for(i =0 ; i<detailsAttrs.length;i++){
        if(detailsAttrs[i]){
            var attribute = new Attribute(detailsAttrs[i],detailsValues[i]);
            massRequest.details.push(attribute);
        }                  
    };
    console.log('details parsed');

    lines.forEach(function(line){
        var lineValues = line.split(',');
        var attributes = [];
        var retLine = new Line();
        for(i =0 ; i<linesAttrs.length;i++){
            // if(linesAttrs[i] == 'AttributesList'){
            //     retLine.attributesList(parsAttributesList(lineValues[i]));
            //     retLine.isAttributeListExist(true);
            // }
            if(linesAttrs[i] == 'Request Line Number' || linesAttrs[i] == 'RequestLineNumber'){
                retLine.lineNumber(lineValues[i]);
                
            }
            retLine.attributes.push(new Attribute(linesAttrs[i],lineValues[i]));
            
        }
        console.log(retLine);
        massRequest.lines.push(retLine);
    })
    console.log('lines parsed');

    return massRequest;

}