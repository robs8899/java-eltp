# java-eltp

EL based template processor.

Current implementation is based on the Expression Language 2.2, a part of JSR 245 (<https://jcp.org/aboutJava/communityprocess/mrel/jsr245/index.html>).

Planned to update to EL 3.0, specified in JSR 341 (<https://jcp.org/aboutJava/communityprocess/final/jsr341/index.html>)

## How to create a template

### Basic syntax

Basic syntax is the EL-Syntax described in the spec. at <https://download.oracle.com/otndocs/jcp/expression_language-2.2-mrel-eval-oth-JSpec/>.

### Instructions

Instructions are additional elements of the template processor, not related to the EL-spec.

var: place a variable in the variable mapper

    #[var:varName,#{testDate}]

date: print a formatted date

    #[date:#{testDate},#{dateFormat}]

number: print a formatted number

    #[number:#{testNumber},#{numberFormat}]

include: include another template

    #[include:include1.txt]

iterate: iterate over a collection

    #[iterate:#{testList},item,idx,iterate1.txt] 

## How to render a template

Create a resource loader: Resource loaders are used to load the templates. Currently we have resource loaders for class path resources and servlet resources.

    ResourceLoader loader = new ClassPathLoader("de/robs/eltp/test/", "utf-8");

Create resolvers for the model: The model is simply a Map with names and objects. A list of EL-Resolvers is used to resolve the names for each model map. 

    Map<String, Object> modelMap = new HashMap<String, Object>();
    Collection<ELResolver> resolvers = new ArrayList<ELResolver>();
    resolvers.add(new StdELResolver(modelMap));

    // TODO: Place objects in the model map.

Create a Rendering Context: The rendering context brings the resource loader and the resolvers together.

    RenderingContext context = new RenderingContext(loader, resolvers);

Start a rendering: simply call

    String rendered = context.render("template.txt")
