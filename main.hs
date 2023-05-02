{-# LANGUAGE BlockArguments #-}
module Main where
import GHC.Generics (Generic)
import qualified Data.Map as Map
import Distribution.Compat.CharParsing
import Data.Char
import Text.XHtml (input, face)
import Control.Applicative

main :: IO()
main = 
        do print (runParser parseJSON "{\"JsonNull\": null, \"JsonBool\": True, \"JsonInt\": 21, \"JString\": \"In the beginning the Universe was created. This has made a lot of people very angry and been widely regarded as a bad move.\", \" JsonArray\": [8, 16, 64]}")

data JValue = JNull
                | JBool Bool
                | JString String
                | JInt Int
                | JArray [JValue]
                | JObject [(String, JValue)]
        deriving (Show, Eq)


newtype Parser a = Parser { runParser :: String -> Maybe (String, a)}

--Functor Applicative en Alternative overgenomen van Tsoding op youtube,
--Specifiek uit de video "JSON Parser 100% From Scratch in Haskell (only 111 lines)";
--https://www.youtube.com/watch?v=N9RUqGYuGfw
instance Functor Parser where 
        fmap f (Parser p) = 
                Parser $ \input -> do
                        (input', x) <- p input
                        Just (input', f x)

instance Applicative Parser where
        pure x = Parser $ \input -> Just (input, x)
        (Parser p1) <*> (Parser p2) = 
                Parser $ \input -> do
                        (input', f) <- p1 input
                        (input'', a) <- p2 input'
                        Just (input'', f a)

instance Alternative Parser where
        empty = Parser $ const Nothing
        (Parser p1) <|> (Parser p2) =
                Parser $ \input -> p1 input <|> p2 input

--Base parsers
parseChar :: Char -> Parser Char
parseChar chr = Parser f
        where
                f (y:ys)
                        | y == chr = Just (ys,chr)
                        | otherwise = Nothing
                f [] = Nothing

parseExpectedString :: String -> Parser String
parseExpectedString = traverse parseChar

searchFor :: [String] -> Parser String
searchFor [x] = allowWhiteSpace (parseExpectedString x)
searchFor x = allowWhiteSpace (parseExpectedString (head x)) <|> searchFor (tail x)

parseSpan ::(Char -> Bool) -> Parser String
parseSpan func = Parser f
                where
                        f [] = Nothing
                        f str = let (int, rest) = span func str 
                                in if null int 
                                        then Nothing 
                                        else Just (rest, int)

parseSurroundBy :: Parser a -> Parser b -> Parser a
parseSurroundBy element surrounder = allowWhiteSpace (surrounder *> element <* surrounder)

--Zorgt er voor dat er lege ruimte voor en na de gegeven parser toegestaan is. Zonder kan bijv. [1, 2, 3] niet.
allowWhiteSpace :: Parser a -> Parser a
allowWhiteSpace element = space *> element <* space <|> space *> element <|> element <* space <|> element
        where space = parseSpan isSpace

parseSeparator :: Parser a -> Parser b -> Parser [a]
parseSeparator element seperator = (:) <$> element <*> many (allowWhiteSpace seperator *> element) <|> pure []

--JSON parsers
parseJNull :: Parser JValue
parseJNull = JNull <$ searchFor["null", "Null", "NULL"]

parseJBool :: Parser JValue
parseJBool =
         JBool True <$ searchFor["true","True"]
          <|> 
         JBool False <$ searchFor["false","False"]

parseJString :: Parser JValue
parseJString = JString <$> parseSurroundBy (parseSpan (/='"')) (parseChar '"')

parseJInt :: Parser JValue
parseJInt = f <$> allowWhiteSpace (parseSpan isDigit)
        where f ds = JInt $ read ds

--TODO: Spaties tussen de komma's breek nu de parser, als tijd over is fix dit eerst.
parseJArray :: Parser JValue
parseJArray = JArray <$> allowWhiteSpace (parseChar '[' *> values <* parseChar ']')
                where   values = parseSeparator parseJSON seperator
                        seperator = parseChar ','

parseJObject :: Parser JValue
parseJObject = JObject <$> allowWhiteSpace (parseChar '{' *> parseSeparator pair (parseChar ',') <* parseChar '}')
                where pair = (\s _ j -> (s,j)) <$> parseSurroundBy (parseSpan (/='"')) (parseChar '"') <*> parseChar ':' <*> parseJSON


parseJSON :: Parser JValue
parseJSON = parseJNull <|> parseJBool <|> parseJInt <|> parseJString <|> parseJArray <|> parseJObject